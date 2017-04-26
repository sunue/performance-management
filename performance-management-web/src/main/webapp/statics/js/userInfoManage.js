$(function(){
	$(".leftNav").css("height", $(window).height());

	var askAddTeacher ="http://localhost:8080/administrator/addTeacher";

	// 添加教师
	$("#modalAddSubBtn").on("click", function(){
		var $addData ={};
		$("#addForm").find('input,select').each(function(){$addData[this.name]=this.value});
		console.log($addData);
		var $sendData ={
			"id":       $addData.addId,
			"name":     $addData.addName,
			"password": $addData.addPassword,
			"sex":      $addData.addSex,
			"age":      $addData.addAge,
			"title":    $addData.addTitle,
			"admissionTime": toTimestamp($addData.addAdmissionTime),
			"grade": 2,
			"scientificResearchScore": 0,
			"teachingResearchScore": 0,
			"status": $addData.addStatus

		};
		console.log($sendData);

		$.ajax(
                {
                    url: askAddTeacher,
                    type: "post",
                    dataType:"json",
                    data: JSON.stringify($sendData),
                    contentType: "application/json; charset=UTF-8",
                    success:function(result)
                    {
                        if (result.status ==0) 
                        {
                            console.log("添加成功"+result.msg);
                            alert(result.msg);
                            $('#add').modal('hide');
                            // location.reload();                           
                        }
                        else
                        {
                            alert(result.msg);
                            $('#add').modal('hide');
                            // location.reload(); 
                        }
         
                    },
                    
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);

                    }
                    
        });
	});


	function toTimestamp(date) {
		date = date.replace(/-/g,'/')
	    date = new Date(date);
	    var UCT = Date.parse(date);
	    return UCT
	}
})