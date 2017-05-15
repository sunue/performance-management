$(function(){
	var getTheName = "http://localhost:8080/teacher/getTeacherName";
	var logout = "http://localhost:8080/teacher/logout";

	$.ajax({
            url:getTheName,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            success:function(data){
            	if(data.status == 0){
            		$("#userName").html(data.data);

            	}
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
    });

    $("#logout").on("click",function(){
        if (confirm("确认要退出了吗？")){
            $.ajax({
                    url:logout,
                    contentType: "application/json; charset=UTF-8",
                    type:"get",
                    success:function(data){
                        if(data.status ==0){
                            location.href = "../login.html";
                        }
                        else{
                            alert(data.msg);
                        }
            
                        
                    },
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);

                    }
            });

        }

    	

    });


})