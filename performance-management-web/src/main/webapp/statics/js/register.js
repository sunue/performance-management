$(function(){
	var passed =[false, false, false, false, false, false, false,false,false];
	var sendData ={};
    var codeNum =createCode(6);
    var validateId = "http://localhost:8080/teacher/idValidate";
     $("#checkCode").html(codeNum );

	$(".id").blur(function(){
       

		if(/^[0-9]{6,20}$/.test($(this).val())){
            var validId ={ id: $(this).val()};
            $.ajax(
                {
                    url: validateId,
                    type: "get",
                    data: $.param(validId),
                    dataType:"json",
                    contentType: "application/json; charset=UTF-8",
                    success:function(result)
                    {
                        if (result.status ==0) 
                        {
                           
                            $(".idTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
                            passed[0] = true;

                        }
                        else
                        {
                            $(".idTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>"+result.msg+"</span>"); 

                            passed[0] = false;
                        }
         
                    },
                    
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }
                        
            });

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
		if(/^[a-zA-Z0-9]{6,20}$/.test($(this).val())){
        	$(".passwordTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");
        	passed[6] = true;
        }else{
			passed[6] = false;
            $(".passwordTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>请输入6到20位字母数字的密码</span>"); 
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

     $("#checkCode").on("click", function(){
        var showCode = createCode(6)
        $(this).html(showCode );
        $(this).attr("class","code");
        codeNum = showCode;
      
    });
    $(".testWord").blur(function(){
        if($(".testWord").val() === codeNum){
            passed[8] = true;
            $(".testWordTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span></span>");


        }
        else{
             passed[8] = false;
             $(".testWordTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>验证码输入错误</span>");
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
       	var askAddress ="http://localhost:8080/teacher/teacherRegister";
       	$("form").find('input,select').each(function(){sendData[this.name]=this.value});
       	console.log(sendData);
        delete sendData.testWord;
        delete sendData.password2;
        delete sendData.admissionTime;
        sendData.admissionTime ="";
        
        sendData.scientificResearchScore ="";
        sendData.teachingResearchScore ="";
        sendData.status="";
        ssendData.grade = 1;
       	$.ajax(
                {
                    url: askAddress,
                    type: "post",
                    data: JSON.stringify(data),
                    dataType:"json",
                    contentType: "application/json; charset=UTF-8",
                    success:function(result)
                    {
                        if (result.status ==0) 
                        {
                            console.log("添加成功"+result.msg);
                                               
                        }
                        else
                        {
                            console.log(result.msg);
                        }
         
                    },
                    
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }
                    
        });
       }
       else{
       	alert("有不通过的，请检查输入 ");
       }



    });


    function createCode(n){
        var rdm ="";
        for(var i=0; i<n; i++){
            rdm += Math.floor(Math.random() * 10);

        }
        return rdm;

    }





});