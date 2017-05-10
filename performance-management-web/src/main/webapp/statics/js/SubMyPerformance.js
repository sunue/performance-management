$(function(){
	$(".leftNav").css("height", $(window).height());

    // 查询未提交绩效
	var selectPerformance = "http://localhost:8080/teacher/getCheckPerformance";
    var getSciContent ="http://localhost:8080/teacher/getSciContent";
    var getSciProject ="http://localhost:8080/teacher/getSciProjectBySciContent"
    var getSciGrade ="http://localhost:8080/teacher/getSciGradeBySciContentAndSciProject"
	var entryData ={ id:""};
	var searchData ={
		pageSize:3,
		pageNum:1,

	};
    // 查看待审核绩效
    getAllPages();

    // 选项查询
    // 科研内容查询
    createList(getSciContent,"","#sicContent","get",true,function(){
        // 科研项目查询
        var searchSicPro ={
            sciContent: $("#sicContent").attr("value")
        }
        createList(getSciProject,searchSicPro,"#sicProject","post",true,function(){

                $("#sicContent").change(function(){
                    searchSicPro.sciContent =$("#sicContent").val();
                    $("#sicProject").html("");
                    createList(getSciProject,searchSicPro,"#sicProject","post",false,function(){
                        // 科研等级查询
                        var searchSicGrade ={
                            sciProject: $("#sicProject").val(),
                            sciContent: $("#sicContent").val()

                        }
                        createList(getSciGrade,searchSicGrade,"#sicGrade","post",true,function(){
                                $("#sicContent").change(function(){
                                searchSicGrade.sciContent =$("#sicContent").val();
                                searchSicGrade.sciProject =$("#sicProject").val();

                                $("#sicProject").html("");
                                $("#sicGrade").html("");
                                createList(getSciGrade,searchSicGrade,"#sicGrade","post",false,function(){
                                    $("#sicProject").change(function(){
                                        searchSicGrade.sciContent =$("#sicContent").val();
                                        searchSicGrade.sciProject =$("#sicProject").val();

                                        $("#sicGrade").html("");
                                        createList(getSciGrade,searchSicGrade,"#sicGrade","post",false);

                                    });
                                });

                            });
                        });
                    });

                });
            });

        });
    
   
   
   
   

    // $.ajax({
    //             url: getSciContent,
    //             dataType:"json",
    //             contentType: "application/json; charset=UTF-8",
    //             type: "get",
    //             success:function(result)
    //             {
                 
    //                 if(result.status == 0)
    //                 {
    //                     var dataList = result.data;
    //                     $.each(dataList,function(index, ele){ 
    //                         $("#sicContent").append("<option value="+ele+">"+ele+"/option>");
    //                     });
                       
    //                 }
    //                 else
    //                 {
    //                     console.log(result.msg);
    //                     alert(result.msg);
    //                 }
    //             },                                                
    //             error:function(XMLHttpRequest, textStatus, errorThrown)
    //             {
    //                 console.log('错误'+errorThrown);
    //             }     
    // });
  


	// 添加绩效
	// $("#scientificBtn").on("click", function(){
	// 	entryData.category ='科研'
	// 	// 其他参数
	// 	entryData.content = $("#scienceContent").val();
	// 	entryData.project = $("#scienceProject").val();
	// 	entryData.grade = $("#scienceGrade").val();
	// 	sendAjax(entryData,entryPerformance);


	// });
	// $("#teachBtn").on("click", function(){
	// 	entryData.category ='教研'
	// 	// 其他参数
	// 	entryData.content = $("#scienceContent").val();
	// 	entryData.project = $("#scienceProject").val();
	// 	entryData.grade = $("#scienceGrade").val();
	// 	sendAjax(entryData,entryPerformance);
	// });
	

	// 公共函数
	// function sendAjax(data,url){
	// 	$.ajax({
 //                    url: url,
 //                    data: JSON.stringify(data) ,
 //                    dataType:"json",
 //                    contentType: "application/json; charset=UTF-8",
 //                    type: "post",
 //                    success:function(result)
 //                    {
                     
 //                        if(result.status == 0)
 //                        {
 //                            console.log(result.msg);
 //                            alert(result.msg);
                           
 //                        }
 //                        else
 //                        {
 //                            console.log(result.msg);
 //                            alert(result.msg);
 //                        }
 //                    },                                                
 //                    error:function(XMLHttpRequest, textStatus, errorThrown)
 //                    {
 //                        console.log('错误'+errorThrown);
 //                    }     
 //        });
	// }



    // --------函数部分
	// 分页请求
	function getAllPages()
    {
        $.ajax({
            url:selectPerformance,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param(searchData),
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
                            searchData.pageNum =page;
                            $(".infoPart").find("tbody").empty();
                            $.ajax(
                                {
                                    url: selectPerformance ,
                                    type: "get",
                                    data: $.param(searchData),
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
                                            $(".infoPart").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+result.msg+"</td></tr>  ");
                                            
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
    //
    function initThePage(results){
        $.each(results,function(index, ele){
            if(ele.status == "2"){
                var status ="待审核"
            }
            $(".infoPart").find("tbody").append(
                "<tr class='info'><td>"+ele.category+"</td><td>"+ele.content+"</td><td>"+ele.project+"</td><td>"+ele.proGrade+"</td><td style='color:red'>"+status+"</td><td><button type='button' class='btn btn-warning scientificBtnEdit' data-toggle='modal' data-target='#scientificEdit'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button>  <button type='button' class='btn btn-danger scientificBtnDel' ><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></button></td></tr>");
        });
 
    }
    // 获取联动列表
    function createList(url,data,selectId,methods,firstTime,doFunction){
        $.ajax({
                url: url,
                dataType:"json",
                data:$.param(data),
                contentType: "application/json; charset=UTF-8",
                type: methods,
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                        var dataList = result.data;
                        $.each(dataList,function(index, ele){
                            if(index ==0&&firstTime){
                                $(selectId).attr("value",ele);
                            }
                           
                            $(selectId).append("<option value="+ele+">"+ele+"</option>");
                           
                        });
                         doFunction();
                       
                    }
                    else
                    {
                        console.log(result.msg);
                        alert(result.msg);
                    }
                },                                                
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    console.log('错误'+errorThrown);
                }     
        });

    }
})