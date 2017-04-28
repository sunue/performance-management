$(function(){
	var namePass= false;
	var pwdPass = false;
	$("#id").blur(function(){//用户名文本框失去焦点触发验证事件

		if(/^[0-9]{6,20}$/.test($(this).val())){
        	$("#userTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>输入正确</span>");
        	namePass = true;
        	
        }else{

            $("#userTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位的数字</span>"); 

        }
    });  
    $("#password").blur(function(){//用户密码框失去焦点触发验证事件  
        if(/^[0-9]{6,20}$/.test($(this).val())){
        	 $("#pswTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>输入正确</span>");
        	 pwdPass =true;

        }else{

            $("#pswTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位的数字密码</span>");  

        }
  
    });

	$("#logoBtn").on("click", function(){
		$("#logoForm").submit(function(event) {
			if(!namePass&&pwdPass ){
				$("#tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请检查您的输入</span>"); 
				return false;
			}
            return true;
		});

		

	});

	$("#adminLogoBtn").on("click", function(){
		$("#adminLogoForm").submit(function(event) {
			if(!namePass&&pwdPass ){
				$("#tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请检查您的输入</span>"); 
				return false;
			}
            return true;
		});

	});

});