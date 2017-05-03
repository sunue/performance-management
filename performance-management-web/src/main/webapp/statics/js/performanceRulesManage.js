$(function(){
    $(".leftNav").css("height", $(window).height());
    
    var askTeachList ="http://localhost:8080/administrator/getTeachingResearchPerformance";
    var askScientificList ="http://localhost:8080/administrator/getScientificResearchPerformance";
    var askScientificListDel ="http://localhost:8080/administrator/deleteScientificResearchPerformance";
    // 科研
    var $searchDataScientific ={
        pageNum:1,
        pageSize:3
    }
  
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
        // 修改
        // $("").each(function(index, ele){
        //     $(ele).on("click", function()
        //     {
        //         var $sendData ={
        //             "id": results[index].virtualId

        //         };
        //         console.log($sendData);
        //         if (confirm("确认同意？")) 
        //         {
        //             $(ele).parent().parent().remove();
        //             $.ajax({
        //                     url: askAgree,
        //                     data: $.param($sendData) ,
        //                     dataType:"json",
        //                     contentType: "application/json; charset=UTF-8",
        //                     type: "get",
        //                     success:function(result)
        //                     {
                             
        //                         if(result.status == 0)
        //                         {
        //                             console.log(result.msg);
        //                             alert(result.msg);
        //                             location.reload();
                                 
                                   
        //                         }
        //                         else
        //                         {
        //                             console.log(result.msg);
        //                             alert(result.msg);
        //                             location.reload();  
        //                         }
        //                     },                                                
        //                     error:function(XMLHttpRequest, textStatus, errorThrown)
        //                     {
        //                         console.log('错误'+errorThrown);
        //                     }     
        //             });
        //         }
        //     });
        // });

        // 删除  ？？？？格式不正确
        $(".scientificBtnDel").each(function(index, ele){
            $(ele).on("click", function()
            {
                var $idArray =[];
                    $idArray.push((results[index].virtualId).toString()); 
                    var $deleteData = {
                            "virtualIdListJson":$idArray
                        };
                if (confirm("确认删除此项？")) 
                {
                    $(ele).parent().parent().remove();
                    $.ajax({
                            url: askScientificListDel,
                            data: $.param($idArray) ,
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


        // 查看并修改
        $(".scientificBtnEdit").each(function(index, ele){
            $(ele).on("click", function(){
                $("#editSciContent").val(results[index].sciContent);
                $("#editSciProject").val(results[index].sciProject);
                $("#editSciGrade").val(results[index].sciGrade);
                $("#editSciScore").val(results[index].sciScore);

                $("#editScientificSub").on("click", function(){



                });

            });


        });

 
    }

})

