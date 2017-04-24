$(function(){
	var passed =[false, false, false, false, false, false, false,false];
	var sendData ={};

	$(".idNum").blur(function(){

		if(/^[0-9]{6,20}$/.test($(this).val())){
        	$(".idTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[0] = true;

        }else{

            $(".idTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到16位的数字</span>"); 
			passed[0] = false;

        }
    });
    $(".sex").blur(function(){
		if($(this).val()){
        	$(".sexTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[1] = true;
        }else{

            $(".sexTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请选择性别</span>"); 
			passed[1] = false;
        }
    });
    $(".name").blur(function(){
		if(/^[\u4E00-\u9FA5A-Za-z]{2,16}$/.test($(this).val())){
        	$(".nameTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[2] = true;
        }else{

            $(".nameTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入2到16位的由字母数字组成的字符串</span>"); 
			passed[2] = false;
        }
    });
    $(".age").blur(function(){
		if($(this).val()>0){
        	$(".ageTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[3] = true;
        }else{

            $(".ageTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入不小于0的年龄</span>"); 
			passed[3] = false;
        }
    });
    $(".title").blur(function(){
		if($(this).val()){
        	$(".titleTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[4] = true;
        }else{
			passed[4] = false;
            $(".titleTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请选择职称</span>"); 
        }
    });
    $(".admissionTime").blur(function(){
		if($(this).val()){
        	$(".admissionTimeTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[5] = true;
        }else{
			passed[5] = false;
            $(".admissionTimeTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请选择入教时间</span>"); 
        }
    });
    $(".password").blur(function(){
		if(/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$/.test($(this).val())){
        	$(".passwordTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[6] = true;
        }else{
			passed[6] = false;
            $(".passwordTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位的密码，不能为纯英文或者数字</span>"); 
        }
    });
    $(".password2").blur(function(){
    	if($(".password").val()){
			if($(this).val()===$(".password").val()){
	        	$(".passwordTip2").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
	        	passed[7] = true;
	        }else{
				passed[7] = false;
	            $(".passwordTip2").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>2次输入的密码不一致</span>");
	            $(".passwordTip2").val('');
	            $(".passwordTip").val(''); 
	        }
    	}
    	else{

    		 $(".passwordTip2").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请先输入密码</span>");

    	}
		
    });

    $("#sub").on("click", function(){
    	var isTruePass = true;
    	// 取消
    	$("form").submit(function(event) {
            return false;
        });
       for(var i=0; i<passed.length; i++){
	       	if(!passed[i]){
				isTruePass = false;
	       	}
       }
       if (isTruePass) {
       	// http://127.0.0.1:8080/performance-management-web/teacher/teacherRegister
       	var askAddress ="http://127.0.0.1:8080/performance-management-web/teacher/teacherRegister";
       	$("form").find('input,select').each(function(){sendData[this.name]=this.value});
       	console.log(sendData);
       	// $.ajax(
        //         {
        //             url: askAddress,
        //             type: "post",
        //             data: JSON.stringify(sendData),
        //             dataType:"json",
        //             contentType: "application/json; charset=UTF-8",
        //             success:function(result)
        //             {
        //                 if (result.status ==0) 
        //                 {
        //                     console.log("添加成功"+result.msg);
        //                     alert(result.msg); 
        //                     $('#addList').modal('hide');
        //                     location.reload();                           
        //                 }
        //                 else
        //                 {
        //                     console.log(result.msg);
        //                     alert(result.msg);
        //                     $('#addList').modal('hide');
        //                     location.reload();
        //                 }
         
        //             },
                    
        //             error:function(XMLHttpRequest, textStatus, errorThrown)
        //             {
        //                 console.log('错误'+errorThrown);
        //             }
                    
        // });
       }
       else{
       	alert("有不通过的 ");
       }



    });





});