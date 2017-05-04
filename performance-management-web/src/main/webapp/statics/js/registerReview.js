$(function(){
    $(".leftNav").css("height", $(window).height());
    
    var askRegisterNum = "http://localhost:8080/performance-management-web/administrator/teacherRegisterCheckSum";
    var askList ="http://localhost:8080/administrator/teacherRegisterCheck";
    var askAgree  ="http://localhost:8080/administrator/teacherRegisterAgree";
    var askDeny ="http://localhost:8080/administrator/teachersRegisterFail";
    var $searchData ={
        pageNum:1,
        pageSize:3
    }
    // 查询待审核人数
    $.ajax({
            url:askRegisterNum,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            success:function(data){
        
                $(".waitReviewNum").html(data.data);
                if(data.status ==3){
                    $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='7'>暂无数据</td></tr>");
                }
                else if(data.status ==3||data.status ==2){
                    $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='7'>查询有误</td></tr>");
                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
    });
    getAllPages();

    function getAllPages()
    {
        $.ajax({
            url:askList,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($searchData),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePage(dataList);

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
                                    url: askList ,
                                    type: "get",
                                    data: $.param($searchData),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePage(dataList);

                                        }
                                        else
                                        {
                                            $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");
                                            
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
                else if (data.status ==3) {
                    $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");


                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }
    function initThePage(results){
        $.each(results,function(index, ele){
            $(".infoPart").find("tbody").append(
                "<tr class='info'><td>"+ele.id+"</td><td>"+ele.name+"</td><td>"+ele.sex+"</td><td>"+ele.title+"</td><td>"+ele.id+"</td><td>"+getLocalTime(ele.admissionTime)+"</td><td><button type='button' class='btn btn-danger btnDeny' ><span class='glyphicon glyphicon-remove' aria-hidden='true'></span> 拒绝</button>  <button class='btn btn-success btnPass'><span class='glyphicon glyphicon-ok' aria-hidden='true'></span> 通过</button></td></tr>");
        });
        // 同意
        $(".btnPass").each(function(index, ele){
            $(ele).on("click", function()
            {
                var $sendData ={
                    "id": results[index].id

                };
                console.log($sendData);
                if (confirm("确认同意？")) 
                {
                    $(ele).parent().parent().remove();
                    $.ajax({
                            url: askAgree,
                            data: $.param($sendData) ,
                            dataType:"json",
                            contentType: "application/json; charset=UTF-8",
                            type: "get",
                            success:function(result)
                            {
                             
                                if(result.status == 0)
                                {
                                    console.log(result.msg);
                                    alert(result.msg);
                                    location.reload();
                                 
                                   
                                }
                                else
                                {
                                    console.log(result.msg);
                                    alert(result.msg);
                                    location.reload();  
                                }
                            },                                                
                            error:function(XMLHttpRequest, textStatus, errorThrown)
                            {
                                console.log('错误'+errorThrown);
                            }     
                    });
                }
            });
        });
        // 拒绝
        $(".btnDeny").each(function(index, ele){
            $(ele).on("click", function()
            {
                var $sendData ={
                    "id": results[index].id
                };
                console.log($sendData);
                if (confirm("确认拒绝通过？")) 
                {
                    $(ele).parent().parent().remove();
                    $.ajax({
                            url: askDeny,
                            data: $.param($sendData) ,
                            dataType:"json",
                            contentType: "application/json; charset=UTF-8",
                            type: "get",
                            success:function(result)
                            {
                             
                                if(result.status == 0)
                                {
                                    console.log(result.msg);
                                    alert(result.msg);
                                    location.reload();  
                                }
                                else
                                {
                                    console.log(result.msg);
                                    alert(result.msg);
                                    location.reload();  
                                }
                            },                                                
                            error:function(XMLHttpRequest, textStatus, errorThrown)
                            {
                                console.log('错误'+errorThrown);
                            }     
                    });
                }
            });
        });
 
    }


    // 
    function getLocalTime(nS) {     
       return new Date(parseInt(nS) * 1000).toLocaleString().replace(/:\d{1,2}$/,' ');     
    }  

})
