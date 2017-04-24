$(function(){
	var namePass= false;
	var pwdPass = false;
	$("#userId").blur(function(){//用户名文本框失去焦点触发验证事件

		if(/[a-zA-Z0-9]{3,16}/.test($(this).val())){
        	$("#userTip").html("<span style='color:green'>输入正确</span>");
        	namePass = true;
        	
        }else{

            $("#userTip").html("<span style='color:red'>请输入3到16位的由字母数字组成的字符串</span>"); 

        }
    });  
    $("#password").blur(function(){//用户密码框失去焦点触发验证事件  
        if(/[a-zA-Z0-9]{6,16}/.test($(this).val())){
        	 $("#pswTip").html("<span style='color:green'>输入正确</span>");
        	 pwdPass =true;

        }else{

            $("#pswTip").html("<span style='color:red'>请输入6到16位的由字母数字组成的字符串</span>");  

        }
  
    });

	$("#logoBtn").on("click", function(){
		$formData={};
		$("#logoForm").find('input').each(function(){$formData[this.name]=this.value});
	    console.log($formData); 
	    if(namePass&&pwdPass){
	    	alert('send ajax!');    	
	    } 
	    else{
	    	alert('no'); 
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

});