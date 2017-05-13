$(function(){
    $(".leftNav").css("height", $(window).height());
    var totalRank ="http://localhost:8080/administrator/totalRank";
    var sicRank ="http://localhost:8080/administrator/scientificResearchRank";
    var teaRank ="http://localhost:8080/administrator/teachingResearchRank";
    var $searchData ={
        pageNum:1,
        pageSize:3
    }

    // 默认查询总分排名
    getAllPages(totalRank);
    $("#searchBtn").on("click", function(){
        // 取消表单默认事件
        $("#searchForm").submit(function(event){
            return false;

        })
        $(".infoPart").find("tbody").empty();
        $("#searchForm").find('input,select').each(function(){$searchData[this.name]=this.value});
        console.log($searchData);
        if($searchData.type ==2){
            // 总分
            getAllPages(totalRank);

        }
        else{
            if($searchData.type ==0){
                // 科研
                getAllPages(sicRank);

            }
            else{
                // 教研
                getAllPages(teaRank);
            }

        }
        

    })


    // -----------函数部分

    function getAllPages(url)
    {
        $.ajax({
            url:url,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($searchData),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePage(dataList,1);

                    var options = {
                        bootstrapMajorVersion: 3, //版本
                        currentPage: 1, //当前页数
                        totalPages: Math.ceil((data.total)/(data.pageSize)), //总页数
                        itemTexts: function (type, page, current) {
                            switch (type) {
                                case "first":
                                    return "首页";
                                case "prev":
                                    return "上一页";
                                case "next":
                                    return "下一页";
                                case "last":
                                    return "末页";
                                case "page":
                                    return page;
                            }
                        },//点击事件，用于通过Ajax来刷新整个list列表
                        onPageClicked: function (event, originalEvent, type, page)
                        {
                            $searchData.pageNum =page;
                            $(".infoPart").find("tbody").empty();
                            $.ajax(
                                {
                                    url: url ,
                                    type: "get",
                                    data: $.param($searchData),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePage(dataList,page);

                                        }
                                        else
                                        {
                                            $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='13'>"+data.msg+"</td></tr>  ");
                                            
                                        }
                         
                                    },
                                    
                                    error:function(XMLHttpRequest, textStatus, errorThrown)
                                    {
                                        console.log('错误'+errorThrown);

                                    }
                                    
                            });
                        }
                    };
                    if(options.totalPages>0){
        
                        $('#pages').bootstrapPaginator(options);
                    }

                }
                else{
                    $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='13'>"+data.msg+"</td></tr>  ");
                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePage(results,page){
        $.each(results,function(index, ele){
            var grade = '';
            var status ='';
            if(ele.grade ==1){
                grade = "管理员";

            }
            else{
                grade = "教师";
            }
            $(".infoPart").find("tbody").append(
                "<tr class='info'><td>"+(index+(page-1)*3+1)+"</td><td>"+ele.id+"</td><td>"+ele.name+"</td><td>"+ele.sex+"</td> <td>"+ele.age+"</td><td>"+ele.title+"</td> <td>"+getLocalTime(ele.admissionTime).substring(0,10)+"</td> <td>"+grade+"</td><td>"+ele.scientificResearchScore+"</td><td>"+ele.teachingResearchScore+"</td><td>"+(ele.scientificResearchScore+ele.teachingResearchScore)+"</td></tr>");
        });
 
    }


    // 时间转换
    function getLocalTime(n) {     
       return new Date(n).format('yyyy-MM-dd hh:mm:ss');     
    }

    //处理日期格式
    Date.prototype.format = function(fmt)
    {
      var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      return fmt;
    }


})

