$(function(){
    $(".leftNav").css("height", $(window).height());
    
    //查看已通过绩效
    var askPassPerformance ="http://localhost:8080/teacher/getPassPerformance";
    
    var $pass ={
        pageNum:1,
        pageSize:5
    }
  
    getPassPerformance();

    function getPassPerformance()
    {
        $.ajax({
            url:askPassPerformance,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($pass),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePagePass(dataList);

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
                            $pass.pageNum =page;
                            $("#passPerformanceForm").find("tbody").empty();
                            $.ajax(
                                {
                                    url: askPassPerformance ,
                                    type: "get",
                                    data: $.param($pass),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePagePass(dataList);

                                        }
                                        else
                                        {
                                            $("#passPerformanceForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");
                                            
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
        
                        $('#passPerformancePages').bootstrapPaginator(options);
                    }

                }
                else if (data.status ==3) {
                    $("#passPerformanceForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");


                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePagePass(results){
    		
        $.each(results,function(index, ele){
        	var status='';
        	if("0"==ele.status){
        		status="已通过";
        	}
            $("#passPerformanceForm").find("tbody").append(
                "<tr class='info'><td align='center'>"+ele.category+"</td><td align='center'>"+ele.content+"</td><td align='center'>"+ele.project+"</td><td align='center'>"+ele.proGrade+"</td><td style='width:154px' align='center'><button class='btn btn-success' disabled='disabled' >"+status+"</button></td></tr>");
        });
    }


    // ---------------分界线
    	
    var askCheckPerformance ="http://localhost:8080/teacher/getCheckPerformance";
    // 教研请求数据
    var $check ={
        pageNum:1,
        pageSize:5
    }

    getCheckPerformance();

    function getCheckPerformance()
    {
        $.ajax({
            url:askCheckPerformance,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($check),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePageCheck(dataList);

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
                            $check.pageNum =page;
                            $("#checkPerformanceForm").find("tbody").empty();
                            $.ajax(
                                {
                                    url: askCheckPerformance ,
                                    type: "get",
                                    data: $.param($check),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePageCheck(dataList);

                                        }
                                        else
                                        {
                                            $("#checkPerformanceForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");
                                            
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
        
                        $('#checkPerformancePages').bootstrapPaginator(options);
                    }

                }
                else if (data.status ==3) {
                    $("#checkPerformanceForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");


                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePageCheck(results){
        console.log(results);
        $.each(results,function(index, ele){
        	var status='';
        	if("2"==ele.status){
        		status="待审核";
        	}
            $("#checkPerformanceForm").find("tbody").append(
                "<tr class='info'><td align='center'>"+ele.category+"</td><td align='center'>"+ele.content+"</td><td align='center'>"+ele.project+"</td><td align='center'>"+ele.proGrade+"</td><td style='width:154px' align='center'><button class='btn btn-warning' disabled='disabled' >"+status+"</button></td></tr>");
        });

    }

    
    // ---------------分界线
    var askFailPerformance ="http://localhost:8080/teacher/getFailPerformance";
    
    var $fail ={
        pageNum:1,
        pageSize:5
    }

    getFailPerformance();

    function getFailPerformance()
    {
        $.ajax({
            url:askFailPerformance,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($fail),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePageFail(dataList);

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
                            $fail.pageNum =page;
                            $("#failPerformanceForm").find("tbody").empty();
                            $.ajax(
                                {
                                    url: askFailPerformance ,
                                    type: "get",
                                    data: $.param($fail),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePageFail(dataList);

                                        }
                                        else
                                        {
                                            $("#failPerformanceForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");
                                            
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
        
                        $('#failPerformancePages').bootstrapPaginator(options);
                    }

                }
                else if (data.status ==3) {
                    $("#failPerformanceForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");


                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePageFail(results){
        console.log(results);
        $.each(results,function(index, ele){
        	var status='';
        	if("3"==ele.status){
        		status="未通过";
        	}
            $("#failPerformanceForm").find("tbody").append(
                "<tr class='info'><td align='center'>"+ele.category+"</td><td align='center'>"+ele.content+"</td><td align='center'>"+ele.project+"</td><td align='center'>"+ele.proGrade+"</td><td style='width:154px' align='center'><button class='btn btn-danger' disabled='disabled' >"+status+"</button></td></tr>");
        });

    }

})

