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
        if(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test($(this).val())){
        	 $("#pswTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>输入正确</span>");
        	 pwdPass =true;

        }else{

            $("#pswTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位字母数字的密码，不能为纯英文或者数字</span>");  

        }
  
    });

	$("#logoBtn").on("click", function(){
		var $formData={};
		$("#logoForm").submit(function(event) {
            return false;
		});
		$("#logoForm").find('input').each(function(){$formData[this.name]=this.value});
	    console.log($formData); 
	    if(namePass&&pwdPass){
	    	alert('send ajax!');    	
	    } 
	    else{
	    	$("#tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>"+请检查您的输入+"</span>"); 

	    }
		// $.ajax({
		// 	url:"/gateway_admin/user/login",
		// 	data:JSON.stringify($formData),
		// 	contentType: "application/json; charset=UTF-8",
		// 	type:"post",
		// 	success:function(data){
		// 		if (data.status ==0) {
					
		// 			$("#tip").html("<span style='color:green'>"+data.msg+"</span>");
		// 			window.location.href="index.html#"+$formData.username;
		// 		}
		// 		else if (data.status==1) {
		// 			$("#tip").html("<span style='color:green'>"+data.msg+"</span>");

		// 		}
		// 	},
		// 	error:function(XMLHttpRequest, textStatus, errorThrown)
		// 	{
		// 		console.log('错误'+errorThrown);

		// 	}
		// });

	});

	$("#adminLogoBtn").on("click", function(){
		$("#logoForm").submit(function(event) {
			if(!namePass&&pwdPass ){
				$("#tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请检查您的输入</span>"); 
				return false;
			}
            return true;
		});

	});

});