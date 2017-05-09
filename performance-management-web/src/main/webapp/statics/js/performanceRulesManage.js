$(function(){
    $(".leftNav").css("height", $(window).height());
    
    var askTeachList ="http://localhost:8080/administrator/getTeachingResearchPerformance";

    var askScientificList ="http://localhost:8080/administrator/getScientificResearchPerformance";
    var askScientificAdd ="http://localhost:8080/administrator/addScientificResearchPerformance";
    var askScientificEdit ="http://localhost:8080/administrator/updateScientificResearchPerformance";
    var askScientificListDel ="http://localhost:8080/administrator/deleteScientificResearchPerformance";
    // 科研
    var $searchDataScientific ={
        pageNum:1,
        pageSize:3
    }
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
                            location.reload();  
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
        $.each(results,function(index, ele){
            $("#scientificResearchForm").find("tbody").append(
                "<tr class='info'><td><input class='check_one check' type='checkbox'/></td><td>"+ele.sciContent+"</td><td>"+ele.sciProject+"</td><td>"+ele.sciGrade+"</td><td>"+ele.sciScore+"</td><td><button type='button' class='btn btn-warning scientificBtnEdit' data-toggle='modal' data-target='#scientificEdit'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button>  <button type='button' class='btn btn-danger scientificBtnDel' ><span class='glyphicon glyphicon-minus' aria-hidden='true'></span></button></td></tr>");
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
        // 删除已选  多个删除
        $("#deleteAllScientific").on("click", function(){
            if (confirm("确认删除已选吗？")) 
            {

                var $idArray =[];
                $(".check_one").each(function(index, ele){
                    
                    if ($(ele).is(':checked')) 
                    {
                        $idArray.push((results[index].virtualId).toString());
                        $(ele).parent().parent().remove();

                    }

                });
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

})

