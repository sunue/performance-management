$(function(){
    $(".leftNav").css("height", $(window).height());
    var getTeacherRank ="http://localhost:8080/teacher/getTeacherRank";


    $.ajax({
                url: getTeacherRank,
                dataType:"json",
                contentType: "application/json; charset=UTF-8",
                type: "get",
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                        console.log(result.msg);
                        $(".scientificRank").html("第"+result.data.scientificResearchRank+"名");
                        $(".teachRank").html("第"+result.data.scientificResearchRank+"名");
                        $(".totalRank").html("第"+result.data.totalRank+"名");

                       
                    }
                    else
                    {
                        console.log(result.msg);
                        $(".info").html("<td colspan='3'>"+result.msg+"</td>");
                    }
                },                                                
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    console.log('错误'+errorThrown);
                }     
    });
    // $.ajax({
    //             url: teachRank,
    //             data: $.param(searchData) ,
    //             dataType:"json",
    //             contentType: "application/json; charset=UTF-8",
    //             type: "get",
    //             success:function(result)
    //             {
                 
    //                 if(result.status == 0)
    //                 {
    //                     console.log(result.msg);
    //                     $(".teachRank").html(result.msg);
                       
    //                 }
    //                 else
    //                 {
    //                     console.log(result.msg);
    //                     $(".teachRank").html(result.msg);
    //                 }
    //             },                                                
    //             error:function(XMLHttpRequest, textStatus, errorThrown)
    //             {
    //                 console.log('错误'+errorThrown);
    //             }     
    // });
    // $.ajax({
    //             url: totalRank,
    //             data: $.param(searchData) ,
    //             dataType:"json",
    //             contentType: "application/json; charset=UTF-8",
    //             type: "get",
    //             success:function(result)
    //             {
                 
    //                 if(result.status == 0)
    //                 {
    //                     console.log(result.msg);
    //                     $(".totalRank").html(result.msg);
                       
    //                 }
    //                 else
    //                 {
    //                     console.log(result.msg);
    //                     $(".totalRank").html(result.msg);
    //                 }
    //             },                                                
    //             error:function(XMLHttpRequest, textStatus, errorThrown)
    //             {
    //                 console.log('错误'+errorThrown);
    //             }     
    // });

  
})