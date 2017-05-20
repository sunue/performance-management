$(function(){
    $(".leftNav").css("height", $(window).height());
    
    var askList ="http://localhost:8080/administrator/teacherPerformanceCheck";
    var askAgree ="http://localhost:8080/administrator/teacherPerformanceAgree";
    var askDeny  ="http://localhost:8080/administrator/teacherPerformanceFail";
    var $searchData ={
        pageNum:1,
        pageSize:6
    }
  
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
                    $(".waitReviewNum").html(data.total);
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
                else{
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
        	
        	var upload='';
            if(null==ele.upload || ""==ele.upload){
            	var upload="<button type='button' class='btn btn-warning downBtn' disabled='disabled'>"+"无"+"</button>"
            	var upload="无";
            }else{
            	upload="<button type='button' class='btn btn-primary downBtn'><span class='glyphicon glyphicon-arrow-down' aria-hidden='true'></span></button>"
            }
        	
            $(".infoPart").find("tbody").append(
                "<tr class='info'><td align='center'>"+ele.id+"</td><td align='center'>"+ele.category+"</td>" +
                "<td align='center'>"+ele.content+"</td><td align='center'>"+ele.project+"</td><td align='center'>"+ele.proGrade+"</td>" +
                "<td style='color:blue' align='center'>"+upload+"</td>" +
                "<td align='center'><button class='btn btn-success btnPass'><span class='glyphicon glyphicon-ok' aria-hidden='true'></span> 通过</button>  <button type='button' class='btn btn-danger btnDeny' ><span class='glyphicon glyphicon-remove' aria-hidden='true'></span> 拒绝</button></td></tr>");
        });
        // 同意
        $(".btnPass").each(function(index, ele){
            $(ele).on("click", function()
            {
                var $sendData ={
                    "virtualId": results[index].virtualId,
                    "id":        results[index].id

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
                    "virtualId": results[index].virtualId
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
                            	console.info(result.status);
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
        
        
        // 下载证明资料
        $(".downBtn").each(function(index, ele){
            $(ele).on("click", function()
            { 
            	console.info("下载证明资料");
                var virtualId=results[index].virtualId;
                
                	console.info("开始");
                	var form=$("<form>");//定义一个form表单
                	form.attr("method","get");
                	form.attr("action","/teacher/down");
                	var input1=$("<input>");
                	input1.attr("type","hidden");
                	input1.attr("name","virtualId");
                	input1.attr("value",virtualId);
                	$("body").append(form);//将表单放置在web中
                	form.append(input1);
                	console.info("提交");
                	form.submit();//表单提交
            });
        });
 
    }

})

