$(function(){
	$(".leftNav").css("height", $(window).height());

    // 查询未提交绩效
	var selectPerformance = "http://localhost:8080/teacher/getCheckPerformance";
    var getSciContent ="http://localhost:8080/teacher/getSciContent";
    var getSciProject ="http://localhost:8080/teacher/getSciProjectBySciContent"
    var getSciGrade ="http://localhost:8080/teacher/getSciGradeBySciContentAndSciProject"

    var getTeaContent ="http://localhost:8080/teacher/getTeaContent"
    var getTeaProject ="http://localhost:8080/teacher/getTeaProjectByTeaContent"
    var getTeaGrade ="http://localhost:8080/teacher/getTeaGradeByTeaContentAndTeaProject"


    var entryPerformance ="http://localhost:8080/teacher/entryPerformance"
    var deleteCheckPerformance= "http://localhost:8080/teacher/deleteCheckPerformance"
    	
    var down="http://localhost:8080/teacher/down"

	var entrySicData ={};
    var entryTeaData ={};
    // 查询数据
    var searchSicPro ={}
    var searchSicGrade ={}
    var searchTeaPro ={}
    var searchTeaGrade ={}

	var searchData ={
		pageSize:5,
		pageNum:1,

	};
    // 查看待审核绩效
    getAllPages();

    // 选项查询
    // 科研内容查询
    createList(getSciContent,"","#sicContent","get",true,function(){
        searchSicPro.sciContent= $("#sicContent").val();
    });

    // 科研项目查询
    createList(getSciProject,searchSicPro,"#sicProject","post",true,function(){
        searchSicGrade.sciContent =$("#sicContent").val();
        searchSicGrade.sciProject =$("#sicProject").val();
    });
    $("#sicContent").change(function(){
        searchSicPro.sciContent =$("#sicContent").val();
        $("#sicContent").attr("value",searchSicGrade.sciContent);
        $("#sicProject").html("");
        createList(getSciProject,searchSicPro,"#sicProject","post",false,function(){
            searchSicGrade.sciProject =$("#sicProject").val();
            searchSicGrade.sicContent =$("#sicContent").val();

        });

    });
    // 科研等级查询   
    createList(getSciGrade,searchSicGrade,"#sicGrade","post",true);
     $("#sicProject").change(function(){
        searchSicGrade.sciContent =$("#sicContent").val();
        $("#sicContent").attr("value",searchSicGrade.sciContent);
        searchSicGrade.sciProject =$("#sicProject").val();
        $("#sicProject").attr("value", searchSicGrade.sciProject);

        $("#sicGrade").html("");
        createList(getSciGrade,searchSicGrade,"#sicGrade","post",false);

    });
    // =======教研项目查询

    // 教研内容查询
    createList(getTeaContent,"","#teaContent","get",true,function(){
        searchTeaPro.teaContent= $("#teaContent").val();
    });
    // 教研项目查询
    createList(getTeaProject,searchTeaPro,"#teaProject","post",true,function(){
        searchTeaGrade.teaContent =$("#teaContent").val();
        searchTeaGrade.sciProject =$("#teaProject").val();
    });
    $("#teaContent").change(function(){
        searchTeaPro.teaContent =$("#teaContent").val();
        $("#teaContent").attr("value",searchTeaGrade.teaContent);
        $("#teaProject").html("");
        createList(getTeaProject,searchTeaPro,"#teaProject","post",false,function(){
            searchTeaGrade.sciProject =$("#teaProject").val();
            searchTeaGrade.teaContent =$("#teaContent").val();

        });

    });
    // 教研等级查询   
    createList(getTeaGrade,searchTeaGrade,"#teaGrade","post",true);
    $("#teaProject").change(function(){
        searchTeaGrade.teaContent =$("#teaContent").val();
        $("#teaContent").attr("value",searchTeaGrade.teaContent);
        searchTeaGrade.sciProject =$("#teaProject").val();
        $("#teaProject").attr("value", searchTeaGrade.sciProject);

        $("#teaGrade").html("");
        createList(getTeaGrade,searchTeaGrade,"#teaGrade","post",false);

    });
    // $("#teaProject").on("click", function(){


    //  });




    // 添加科研绩效
	$("#sicSub").on("click", function(){
		entrySicData.category ='科研'
		// 其他参数
		entrySicData.content = $("#sicContent").val();
		entrySicData.project = $("#sicProject").val();
		entrySicData.proGrade = $("#sicGrade").val();
		entrySicData.upload=$("#sicUpload").val();
		
		sendAjax(entrySicData,entryPerformance);


	}); 
	
//	//科研上传资料
//	$("uploadSub").on("click",function(){
//		uploadAjax(upload);
//	});
//	
	
    // 添加教研绩效
	$("#teaSub").on("click", function(){
        entryTeaData.category ='教研'
        // 其他参数
        entryTeaData.content = $("#teaContent").val();
        entryTeaData.project = $("#teaProject").val();
        entryTeaData.proGrade = $("#teaGrade").val();
        entryTeaData.upload=$("#teaUpload").val();
        
        sendAjax(entryTeaData,entryPerformance);


    });
	

	



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
            
            var upload='';
            if(null==ele.upload || ""==ele.upload){
            	var upload="<button type='button' class='btn btn-success downBtn' disabled='disabled'>"+"无"+"</button>"
            }else{
            	upload="<button type='button' class='btn btn-success downBtn'><span class='glyphicon glyphicon-arrow-down' aria-hidden='true'></span></button>"
            }
            $(".infoPart").find("tbody").append(
                "<tr class='info'><td align='center'>"+ele.category+
                "</td><td align='center'>"+ele.content+"</td><td align='center'>"+ele.project+
                "</td><td align='center'>"+ele.proGrade+"</td><td style='color:red' align='center'>"+status+"</td>" +
                "<td align='center'><form action='/teacher/down' method='get'>"+upload+"</form></td>" +
                "<td align='center'><button type='button' class='btn btn-danger scientificBtnDel' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button></td></tr>");
            
        }); 
            // 删除待审核绩效
            $(".scientificBtnDel").each(function(index, ele){
                $(ele).on("click", function()
                { 
                    var idArray =[];
                    idArray.push((results[index].virtualId).toString());
                    console.info(idArray);
                    if (confirm("确认删除吗？")) 
                    {
                        $.ajax({
                                url: deleteCheckPerformance,
                                data: JSON.stringify(idArray) ,
                                dataType:"json",
                                contentType: "application/json; charset=UTF-8",
                                type: "post",
                                success:function(result)
                                {
                                    $(ele).parent().parent().remove();
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
            	var $sendData ={
                    	"virtualId": results[index].virtualId	
                  	};
                
                var virtualId=results[index].virtualId;
                
                	console.info("开始");
                	var form=$("<form>");//定义一个form表单
                	//form.attr("style","display:none");
                	//form.attr("target","");
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
    // 获取联动列表
    function createList(url,data,selectId,methods,firstTime,tovalue){
        $.ajax({
                url: url,
                dataType:"json",
                data:$.param(data),
                contentType: "application/x-www-form-urlencoded;charset=utf-8",
                type: methods,
                async:false,
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                        var dataList = result.data;
                        $(selectId).append("<option value=''>请选择 </option>");
                        $.each(dataList,function(index, ele){
                           
                            $(selectId).append("<option value="+ele+">"+ele+"</option>");
                        });
                        if(tovalue){

                            tovalue();
                                   
                        }
                        
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
    // 发送提交请求
    function sendAjax(data,url){
    	/*
    	console.info("开始");
    	var form=$("<form>");//定义一个form表单
    	form.attr("method","post");
    	//form.attr("enctype","multipart/form-data");
    	form.attr("contentType","application/json");
    	form.attr("action","/teacher/entryPerformance");
    	var input1=$("<input>");
    	input1.attr("type","hidden");
    	input1.attr("name","category");
    	input1.attr("value",data.category); //
    	
    	var input2=$("<input>");
    	input2.attr("type","hidden");
    	input2.attr("name","content");
    	input2.attr("value",data.content); //
    	
    	var input3=$("<input>");
    	input3.attr("type","hidden");
    	input3.attr("name","project");
    	input3.attr("value",data.project); //
    	
    	var input4=$("<input>");
    	input4.attr("type","hidden");
    	input4.attr("name","proGrade");
    	input4.attr("value",data.proGrade); //
    	
    	//var input5=$("<input>");
    	//input5.attr("type","file");
    	//input5.attr("name","file");
    	//input5.attr("value",data.proGrade); //
    	
    	$("body").append(form);//将表单放置在web中
    	form.append(input1);
    	form.append(input2);
    	form.append(input3);
    	form.append(input4);
    	//form.append(input5);
    	console.info("提交");
    	form.submit();//表单提交
    	//form.ajaxSubmit(); */
    	
        $.ajax({
                    url: url,
                    data: JSON.stringify(data) ,
                    dataType:"json",
                    contentType: "application/json; charset=UTF-8",
                    type: "post",
                    
                    success:function(result)
                    {
                    	//$("#sciUploadForm").ajaxSubmit();
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
                            //location.reload(); 
                        }
                    },                                                
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }     
        }); 
    }
    
})