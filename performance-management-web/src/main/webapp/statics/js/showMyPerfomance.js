$(function(){
    $(".leftNav").css("height", $(window).height());
    var teachRank = "http://localhost:8080/teacher/teachingResearchRank";
    var scientificRank = "http://localhost:8080/teacher/scientificResearchRank";
    var searchData ={ 
        id:22222
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

  
})