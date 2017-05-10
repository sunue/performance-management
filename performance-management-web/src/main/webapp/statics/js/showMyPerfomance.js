$(function(){
    $(".leftNav").css("height", $(window).height());
    var teachRank = "http://localhost:8080/teacher/teachingResearchRank";
    var scientificRank = "http://localhost:8080/teacher/scientificResearchRank";
    var totalRank ="http://localhost:8080/teacher/totalRank";
    var getTeacherName ="http://localhost:8080/teacher/getTeacherName";
    var teacherId ="222222";
    // $.ajax({
    //             url: getTeacherName,
    //             dataType:"json",
    //             contentType: "application/json; charset=UTF-8",
    //             type: "get",
    //             success:function(result)
    //             {
                 
    //                 if(result.status == 0)
    //                 {
    //                    teacherId = result.data
                       
    //                 }
    //                 else
    //                 {
    //                     console.log(result.msg);
    //                 }
    //             },                                                
    //             error:function(XMLHttpRequest, textStatus, errorThrown)
    //             {
    //                 console.log('错误'+errorThrown);
    //             }     
    // });

    var searchData ={ 
        id:teacherId
    };


    $.ajax({
                url: scientificRank,
                data:$.param(searchData) ,
                dataType:"json",
                contentType: "application/json; charset=UTF-8",
                type: "get",
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                        console.log(result.msg);
                        $(".scientificRank").html(result.msg);
                       
                    }
                    else
                    {
                        console.log(result.msg);
                        $(".scientificRank").html(result.msg);
                    }
                },                                                
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    console.log('错误'+errorThrown);
                }     
    });
    $.ajax({
                url: teachRank,
                data: $.param(searchData) ,
                dataType:"json",
                contentType: "application/json; charset=UTF-8",
                type: "get",
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                        console.log(result.msg);
                        $(".teachRank").html(result.msg);
                       
                    }
                    else
                    {
                        console.log(result.msg);
                        $(".teachRank").html(result.msg);
                    }
                },                                                
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    console.log('错误'+errorThrown);
                }     
    });
    $.ajax({
                url: totalRank,
                data: $.param(searchData) ,
                dataType:"json",
                contentType: "application/json; charset=UTF-8",
                type: "get",
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                        console.log(result.msg);
                        $(".totalRank").html(result.msg);
                       
                    }
                    else
                    {
                        console.log(result.msg);
                        $(".totalRank").html(result.msg);
                    }
                },                                                
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    console.log('错误'+errorThrown);
                }     
    });

  
})