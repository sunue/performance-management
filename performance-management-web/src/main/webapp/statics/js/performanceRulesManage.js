$(function(){
    $(".leftNav").css("height", $(window).height());
    
   
    var askScientificList ="http://localhost:8080/administrator/getScientificResearchPerformance";
    var askScientificAdd ="http://localhost:8080/administrator/addScientificResearchPerformance";
    var askScientificEdit ="http://localhost:8080/administrator/updateScientificResearchPerformance";
    var askScientificListDel ="http://localhost:8080/administrator/deleteScientificResearchPerformance";
    // 科研
    var $searchDataScientific ={
        pageNum:1,
        pageSize:5
    }
    var sicResult ={};
    var teaResult ={};
    // 增加科研选项
    $("#AddSciBtn").on("click", function(){
        var $addData ={};
        $("#addScientificForm").find('input').each(function(){$addData[this.name]=this.value});
        
        $.ajax({
                    url: askScientificAdd,
                    data: JSON.stringify($addData),
                    dataType:"json",
                    contentType: "application/json; charset=UTF-8",
                    type: "post",
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
                            //location.reload();  
                        }
                    },                                                
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }     
        });
    });

  
    getAllPages();

    function getAllPages()
    {
        $.ajax({
            url:askScientificList,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($searchDataScientific),
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
                            $searchDataScientific.pageNum =page;
                            $("#scientificResearchForm").find("tbody").empty();
                            $.ajax(
                                {
                                    url: askScientificList ,
                                    type: "get",
                                    data: $.param($searchDataScientific),
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
                                            $("#scientificResearchForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");
                                            
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
        
                        $('#scientificPages').bootstrapPaginator(options);
                    }

                }
                else if (data.status ==3) {
                    $("#scientificResearchForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");


                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePage(results){
        sicResult =results;
        $.each(results,function(index, ele){
            $("#scientificResearchForm").find("tbody").append(
                "<tr class='info'><td align='center'><input class='check_one check' type='checkbox'/></td><td align='center'>"+ele.sciContent+
                "</td><td align='center'>"+ele.sciProject+"</td><td align='center'>"+ele.sciGrade+"</td><td align='center'>"+ele.sciScore+
                "</td><td style='width:154px' align='center'><button type='button' class='btn btn-warning scientificBtnEdit' data-toggle='modal' data-target='#scientificEdit'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button>  <button type='button' class='btn btn-danger scientificBtnDel' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button></td></tr>");
        });

        // 选择框
        $(".check").each(function(index,ele){
            $(this).click(function(){
                console.log("check")
                // 全选的情况下
                if ($(this).attr("class").indexOf("check_all") >= 0)
                {  
                    var $theChecked = $(this).is(':checked');
                    console.log($theChecked);
                    $(".check").each(function(index,ele){

                        $(this).prop("checked", $theChecked);
                    });

                }
                // 有一个没有被选中的话
                if(!$(this).is(':checked'))
                {
                     $(".check_all").each(function(index,ele){
                        $(ele).prop("checked", false);
                     });
                }
            });
            
        });
        
        // 删除格式正确
        $(".scientificBtnDel").each(function(index, ele){
            $(ele).on("click", function()
            {
                var idArray =[];
                    idArray.push((results[index].virtualId).toString());
                console.info(idArray);
                if (confirm("确认删除此项？")) 
                {
                    
                    $.ajax({
                            url: askScientificListDel,
                            data: JSON.stringify(idArray),
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
       

        // 查看并修改
        $(".scientificBtnEdit").each(function(index, ele){
            $(ele).on("click", function(){
                $("#editSciContent").val(results[index].sciContent);
                $("#editSciProject").val(results[index].sciProject);
                $("#editSciGrade").val(results[index].sciGrade);
                $("#editSciScore").val(results[index].sciScore);

                $("#editScientificSub").on("click", function(){
                    var $editData = {
                        "virtualId":        results[index].virtualId,
                        "sciContent":       $("#editSciContent").val(),
                        "sciProject":       $("#editSciProject").val(),    
                        "sciGrade":         $("#editSciGrade").val(),  
                        "sciScore":         $("#editSciScore").val(),    

                    };

                    // 编辑后提交
                    $.ajax(
                            {
                                url: askScientificEdit,
                                dataType:"json",
                                type:"POST",
                                data:JSON.stringify($editData),
                                contentType: "application/json; charset=UTF-8",
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
                                        alert(result.msg);
                                    }
                                },                                                
                                error:function(XMLHttpRequest, textStatus, errorThrown)
                                {
                                    console.log('错误'+errorThrown);
                                }     
                            });
                    });  //编辑提交结束  

            });


        });
    }

    // 删除已选  多个删除
    $("#deleteAllScientific").on("click", function(){
        if (confirm("确认删除已选吗？")) 
        {

            var $idArray =[];
            $(".check_one").each(function(index, ele){
                
                if ($(ele).is(':checked')) 
                {
                    $idArray.push((sicResult[index].virtualId).toString());
                    $(ele).parent().parent().remove();
                    
                }

            });
            console.info($idArray);
            //--------- 发送删除多行：
            if($idArray.length>0){

                $.ajax({
                    url: askScientificListDel,
                    data:JSON.stringify($idArray),
                    contentType: "application/json; charset=UTF-8",
                    dataType:"json",
                    type: "post",
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
            else{
                return false;
            }
            
        }

    });



    // ---------------分界线
    var askTeachList ="http://localhost:8080/administrator/getTeachingResearchPerformance";
    var askTeachAdd ="http://localhost:8080/administrator/addTeachingResearchPerformance"
    var askTeachEdit ="http://localhost:8080/administrator/updateTeachingResearchPerformance"
    var askTeachDel ="http://localhost:8080/administrator/deteleTeachingResearchPerformance"
    // 教研请求数据
    var $searchTeach ={
        pageNum:1,
        pageSize:5
    }
    // 添加
    
    $("#AddTeaBtn").on("click", function(){
        var $addTeaData ={};
        $("#addTeaForm").find('input').each(function(){$addTeaData[this.name]=this.value});
        
        $.ajax({
                    url: askTeachAdd,
                    data: JSON.stringify($addTeaData),
                    dataType:"json",
                    contentType: "application/json; charset=UTF-8",
                    type: "post",
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
                            //location.reload();  
                        }
                    },                                                
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }     
        });
    });

    getAllPagesTea();

    function getAllPagesTea()
    {
        $.ajax({
            url:askTeachList,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($searchTeach),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePageTea(dataList);

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
                            $searchTeach.pageNum =page;
                            $("#teachResearchForm").find("tbody").empty();
                            $.ajax(
                                {
                                    url: askTeachList ,
                                    type: "get",
                                    data: $.param($searchTeach),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePageTea(dataList);

                                        }
                                        else
                                        {
                                            $("#teachResearchForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");
                                            
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
        
                        $('#teachPages').bootstrapPaginator(options);
                    }

                }
                else if (data.status ==3) {
                    $("#teachResearchForm").find("tbody").html("<tr class='toCenter'><td colspan='7'>"+data.msg+"</td></tr>  ");


                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePageTea(results){
        teaResult =results;
        console.log(results);
        $.each(results,function(index, ele){
            $("#teachResearchForm").find("tbody").append(
                "<tr class='info'><td align='center'><input class='checkTea_one checkTea' type='checkbox'/></td><td align='center'>"+ele.teaContent+"</td><td align='center'>"+ele.teaProject+"</td><td align='center'>"+ele.teaGrade+"</td><td align='center'>"+ele.teaScore+"</td><td style='width:154px' align='center'><button type='button' class='btn btn-warning btnEdit' data-toggle='modal' data-target='#teaProject'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button> <button type='button' class='btn btn-danger btnDel' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button></td></tr>");
        });

        // 选择框
        $(".checkTea").each(function(index,ele){
            $(this).click(function(){
                // 全选的情况下
                if ($(this).attr("class").indexOf("checkTea_all") >= 0)
                {  
                    var $theChecked = $(this).is(':checked');
                    console.log($theChecked);
                    $(".checkTea").each(function(index,ele){

                        $(this).prop("checked", $theChecked);
                    });

                }
                // 有一个没有被选中的话
                if(!$(this).is(':checked'))
                {
                     $(".checkTea_all").each(function(index,ele){
                        $(ele).prop("checked", false);
                     });
                }
            });
            
        });
        
        // 删除格式正确
        $(".btnDel").each(function(index, ele){
            $(ele).on("click", function()
            {
                var idArrayTea =[];
                    idArrayTea.push((results[index].virtualId).toString());
                if (confirm("确认删除此项？")) 
                {             
                    $.ajax({
                            url: askTeachDel,
                            data: JSON.stringify(idArrayTea),
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
       
        // 查看并修改
        $(".btnEdit").each(function(index, ele){
            $(ele).on("click", function(){
                $("#editTeaContent").val(results[index].teaContent);
                $("#editTeaProject").val(results[index].teaProject);
                $("#editTeaGrade").val(results[index].teaGrade);
                $("#editTeaScore").val(results[index].teaScore);

                $("#editTeaentificSub").on("click", function(){
                    var $editDataTea = {
                        "virtualId":        results[index].virtualId,
                        "teaContent":       $("#editTeaContent").val(),
                        "teaProject":       $("#editTeaProject").val(),    
                        "teaGrade":         $("#editTeaGrade").val(),  
                        "teaScore":         $("#editTeaScore").val(),    

                    };

                    // 编辑后提交
                    $.ajax(
                            {
                                url: askTeachEdit,
                                dataType:"json",
                                type:"POST",
                                data:JSON.stringify($editDataTea),
                                contentType: "application/json; charset=UTF-8",
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
                                        alert(result.msg);
                                    }
                                },                                                
                                error:function(XMLHttpRequest, textStatus, errorThrown)
                                {
                                    console.log('错误'+errorThrown);
                                }     
                            });
                    });  //编辑提交结束  

            });


        });
    }

    // 删除已选  多个删除
    $("#deleteAllTeach").on("click", function(){
        if (confirm("确认删除已选吗？")) 
        {

            var $idArrayTeas =[];
            $(".checkTea_one").each(function(index, ele){
                
                if ($(ele).is(':checked')) 
                {
                    $idArrayTeas.push((teaResult[index].virtualId).toString());
                    $(ele).parent().parent().remove();
                }
            });
            //--------- 发送删除多行：
            if($idArrayTeas.length>0){

                $.ajax({
                    url: askTeachDel,
                    data:JSON.stringify($idArrayTeas),
                    contentType: "application/json; charset=UTF-8",
                    dataType:"json",
                    type: "post",
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
            else{
                return false;
            }
            
        }

    });


})

