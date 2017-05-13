$(function(){
	var namePass= false;
	var pwdPass = false;
	var loginAsk ="http://localhost:8080/teacher/teacherLogin";
	var admLoginAsk ="http://localhost:8080/administrator/administratorLogin";
	$("#id").blur(function(){//用户名文本框失去焦点触发验证事件

		if(/^[0-9]{6,20}$/.test($(this).val())){
        	$("#userTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>输入正确</span>");
        	namePass = true;
        	
        }else{

            $("#userTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位的数字</span>"); 

        }
    }); 
    $("#password").blur(function(){//用户密码框失去焦点触发验证事件  
        if(/^[a-zA-Z0-9]{6,20}$/.test($(this).val())){
        	 $("#pswTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>输入正确</span>");
        	 pwdPass =true;

        }else{

            $("#pswTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位的数字字母密码</span>");  

        }
  
    });

	$("#logoBtn").on("click", function(){
		// $("#logoForm").submit(function(event) {
		// 	if(!namePass&&pwdPass ){
		// 		$("#tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请检查您的输入</span>"); 
		// 		return false;
		// 	}
  //           return true;
		// });
		$("#adminLogoForm").submit(function(event) {
			return false;
		});
		var sendData ={};
		$("#logoForm").find("input").each(function(){sendData[this.name] =this.value});
		if(namePass&&pwdPass){
			sendAjax(sendData,loginAsk,"../teacher/SubMyPerformance.html.html")


		}

		

	});

	$("#adminLogoBtn").on("click", function(){
		// $("#adminLogoForm").submit(function(event) {
		// 	if(!namePass&&pwdPass ){
		// 		$("#tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请检查您的输入</span>"); 
		// 		return false;
		// 	}
  //           return true;
		// });
		$("#adminLogoForm").submit(function(event) {
			return false;
		});
		var sendAdmData ={};
		$("#adminLogoForm").find("input").each(function(){sendAdmData[this.name] =this.value});
		if(namePass&&pwdPass){
			sendAjax(sendAdmData,admLoginAsk,"../administrator/managePerformance.html")

		}

	});


	function sendAjax(data,url,turnUrl){
        $.ajax({
                    url: url,
                    data:$.param(data) ,
                    dataType:"json",
                    contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    type: "get",
                    success:function(result)
                    {
                    	switch(result.status){
							case 0:
								$("#tip").html("<span style='color:green'>"+result.msg+"</span>");
	                            location.href = turnUrl;
								break;
							default:
								$("#tip").html("<span style='color:red'>"+result.msg+"</span>");
								break;


                    	}
                     
                      
                    },                                                
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }     
        });
    }

});