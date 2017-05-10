$(function(){
    $(".leftNav").css("height", $(window).height());
    var getTeacherInfo = "http://localhost:8080/teacher/getTeacherInfo";
    var editAsk = "http://localhost:8080/teacher/updateTeacherInfo";
    var searchData ={ 
        id:111111
    };


    $.ajax({
                url: getTeacherInfo,
                data:$.param(searchData) ,
                dataType:"json",
                contentType: "application/json; charset=UTF-8",
                type: "get",
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                       
                     
                    }
                    else
                    {
                        console.log(result.msg);
                        $(".infoPart").find("tbody").html("<td colspan='7' style='color:red; text-align:center'>"+result.msg+"</td>");
                    }
                },                                                
                error:function(XMLHttpRequest, textStatus, errorThrown)
                {
                    console.log('错误'+errorThrown);
                }     
    });
})