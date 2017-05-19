$(function(){
    $(".leftNav").css("height", $(window).height());
    
    var askList ="http://localhost:8080/administrator/getTeacher";
    var addList ="http://localhost:8080/administrator/addTeacher";
    var askEdit = "http://localhost:8080/administrator/updateTeacher";
    var askDel = "http://localhost:8080/administrator/deleteTeacher";

    // 科研
    var $searchData ={
        pageNum:1,
        pageSize:5
    }
    // 增加
    $("#modalAddSubBtn").on("click", function(){
        var $addData ={
        	"grade":2,
	    	"scientificResearchScore": 0,
		    "teachingResearchScore": 0,

        };
        $("#addForm").find('input,select').each(function(){$addData[this.name]=this.value});
        $addData.age = parseInt($addData.age);
        $.ajax({
                    url: addList,
                    data: JSON.stringify($addData),
                    dataType:"json",
                    contentType: "application/json; charset=UTF-8",
                    type: "post",
                    success:function(result)
                    {
                     
                        if(result.status == 0)
                        {
                            console.log(result.msg);
                            alert(result.msg);
                            location.reload();  
                        }
                        else
                        {
                            console.log(result.msg);
                            alert(result.msg);
                            location.reload();  
                        }
                    },                                                
                    error:function(XMLHttpRequest, textStatus, errorThrown)
                    {
                        console.log('错误'+errorThrown);
                    }     
        });
    });

	// 默认查询全部
    getAllPages();
    $("#searchBtn").on("click", function(){
    	// 取消表单默认事件
    	$("#searchForm").submit(function(event){
    		return false;

    	})
    	$(".infoPart").find("tbody").empty();
        $("#searchForm").find('input').each(function(){$searchData[this.name]=this.value});
        getAllPages();

    })


    // -----------函数部分

    function getAllPages()
    {
        $.ajax({
            url:askList,
            contentType: "application/json; charset=UTF-8",
            type:"get",
            data: $.param($searchData),
            success:function(data){
                if(data.status ==0){
                    var dataList =data.data_list;
                    initThePage(dataList);

                    var options = {
                        bootstrapMajorVersion: 3, //版本
                        currentPage: 1, //当前页数
                        totalPages: Math.ceil((data.total)/(data.pageSize)), //总页数
                        itemTexts: function (type, page, current) {
                            switch (type) {
                                case "first":
                                    return "首页";
                                case "prev":
                                    return "上一页";
                                case "next":
                                    return "下一页";
                                case "last":
                                    return "末页";
                                case "page":
                                    return page;
                            }
                        },//点击事件，用于通过Ajax来刷新整个list列表
                        onPageClicked: function (event, originalEvent, type, page)
                        {
                            $searchData.pageNum =page;
                            $("#userInfo").find("tbody").empty();
                            $.ajax(
                                {
                                    url: askList ,
                                    type: "get",
                                    data: $.param($searchData),
                                    contentType: "application/json; charset=UTF-8",
                                    success:function(result)
                                    {
                                        if (result.status ==0) 
                                        {
                                           
                                            var dataList =result.data_list;
                                            initThePage(dataList);

                                        }
                                        else
                                        {
                                            $("#userInfo").find("tbody").html("<tr class='toCenter'><td colspan='13'>"+data.msg+"</td></tr>  ");
                                            
                                        }
                         
                                    },
                                    
                                    error:function(XMLHttpRequest, textStatus, errorThrown)
                                    {
                                        console.log('错误'+errorThrown);

                                    }
                                    
                            });
                        }
                    };
                    if(options.totalPages>0){
        
                        $('#pages').bootstrapPaginator(options);
                    }

                }
                else{
                    $("#userInfo").find("tbody").html("<tr class='toCenter'><td colspan='13'>"+data.msg+"</td></tr>  ");
                }
            
            },
            error:function(XMLHttpRequest, textStatus, errorThrown)
            {
                console.log('错误'+errorThrown);

            }
        });
    }

    function initThePage(results){
        $.each(results,function(index, ele){
        	var status ='';
        	var age='';
        	var admissionTime;
        	switch(ele.status){
        		case "0":
	        		status ="正常";
	        		break;
        		case "1":
	        		status ="删除";
	        		break;
	        	case "2":
	        		status ="审核中";
	        		break;
	        	case "3":
	        		status ="未通过";
	        		break;

        	}
        	if(null==ele.age){
        		age="未知";
        	}else{
        		age=ele.age;
        	}
        	
        	if(null==ele.admissionTime){
        		admissionTime="未知";
        	}else{
        		admissionTime=getLocalTime(ele.admissionTime).substring(0,10);
        	}
            $("#userInfo").find("tbody").append(
                "<tr class='info'><td align='center'><input class='check_one check' type='checkbox'/></td><td align='center'>"+ele.id+"</td><td align='center'>"+ele.name+"</td><td align='center'>"+ele.password+"</td><td align='center'>"+ele.sex+"</td><td align='center'>"+age+"</td><td align='center'>"+ele.title+"</td><td align='center'>"+admissionTime+"</td><td align='center'>"+ele.scientificResearchScore+"</td><td align='center'>"+ele.teachingResearchScore+"</td><td align='center'>"+status+"</td><td align='center'><button type='button' class='btn btn-warning btnEdit' data-toggle='modal' data-target='#seeModal'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span></button> <button type='button' class='btn btn-danger btnDel' ><span class='glyphicon glyphicon-trash' aria-hidden='true'></span></button></td></tr>");
        });

        // 选择框
        $(".check").each(function(index,ele){
            $(this).click(function(){
                console.log("check")
                // 全选的情况下
                if ($(this).attr("class").indexOf("check_all") >= 0)
                {  
                    var $theChecked = $(this).is(':checked');
                    console.log($theChecked);
                    $(".check").each(function(index,ele){

                        $(this).prop("checked", $theChecked);
                    });

                }
                // 有一个没有被选中的话
                if(!$(this).is(':checked'))
                {
                     $(".check_all").each(function(index,ele){
                        $(ele).prop("checked", false);
                     });
                }
            });
            
        });
        
        // 删除 格式正确
        $(".btnDel").each(function(index, ele){
            $(ele).on("click", function()
            {
                var idArray =[];
                    idArray.push((results[index].id).toString()) ;
                if (confirm("确认删除此项？")) 
                {
                   
                    $.ajax({
                            url: askDel,
                            data: JSON.stringify(idArray),
                            dataType:"json",
                            contentType: "application/json; charset=UTF-8",
                            type: "post",
                            success:function(result)
                            {
	                        	$(ele).parent().parent().remove();
                             
                                if(result.status == 0)
                                {
                                    console.log(result.msg);
                                    alert(result.msg);
                                    location.reload();  
                                }
                                else
                                {
                                    console.log(result.msg);
                                    alert(result.msg);
                                    location.reload();  
                                }
                            },                                                
                            error:function(XMLHttpRequest, textStatus, errorThrown)
                            {
                                console.log('错误'+errorThrown);
                            }     
                    });
                }
            });
        });

        // 删除已选  多个删除
	    $("#deleteAll").on("click", function(){
	        if (confirm("确认删除已选吗？")) 
	        {

	            var $idArray =[];
	            $(".check_one").each(function(index, ele){
	                
	                if ($(ele).is(':checked')) 
	                {
	                    $idArray.push(results[index].id);
	                    $(ele).parent().parent().remove();

	                }

	            });
	            console.info($idArray);
	            //--------- 发送删除多行：
	            if($idArray.length>0){

	                $.ajax({
	                    url: askDel,
	                    data:JSON.stringify($idArray),
	                    contentType: "application/json; charset=UTF-8",
	                    dataType:"json",
	                    type: "post",
	                    success:function(result)
	                    {
	                     
	                        if(result.status == 0)
	                        {
	                            console.log(result.msg);
	                            alert(result.msg);
	                            location.reload();     
	                           
	                        }
	                        else
	                        {
	                            console.log(result.msg); 
	                            alert(result.msg);
	                            location.reload();  
	                        }
	                    },                                                
	                    error:function(XMLHttpRequest, textStatus, errorThrown)
	                    {
	                        console.log('错误'+errorThrown);
	                    }     
	               });


	              
	            }
	            else{
	                return false;
	            }
	            
	        }

	    });


        // 查看并修改
        $(".btnEdit").each(function(index, ele){
            $(ele).on("click", function(){
                $("#editId").val(results[index].id);
                $("#editName").val(results[index].name);
                $("#editSex").val(results[index].sex);
                $("#editAge").val(results[index].age);
                $("#editTitle").val(results[index].title);
                $("#editAdmissionTime").val(results[index].admissionTime);
                $("#editScientifiScore").val(results[index].scientificResearchScore);
                $("#editTeachfiScore").val(results[index].teachingResearchScore);
                $("#editAdmissionTime").val(getLocalTime(results[index].admissionTime).substring(0,10));
                $("#editSub").on("click", function(){
                    var $editData = {
                        "virtualId":         results[index].virtualId,
                        "id":                $("#editId").val(),
                        "name":              $("#editName").val(),    
                        "sex":               $("#editSex").val(),  
                        "age":               $("#editAge").val(), 
                        "title":             $("#editTitle").val(),
                        "admissionTime":            $("#editAdmissionTime").val(),    
                        "scientificResearchScore":  $("#editScientifiScore").val(),  
                        "teachingResearchScore":    $("#editTeachfiScore").val()
                    };

                    // 编辑后提交
                    $.ajax(
                            {
                                url: askEdit,
                                dataType:"json",
                                type:"POST",
                                data:JSON.stringify($editData),
                                contentType: "application/json; charset=UTF-8",
                                success:function(result)
                                {
                                 
                                    if(result.status == 0)
                                    {
                                        console.log(result.msg);
                                        alert(result.msg);
                                        location.reload();
                                    }
                                    else
                                    {
                                        alert(result.msg);
                                    }
                                },                                                
                                error:function(XMLHttpRequest, textStatus, errorThrown)
                                {
                                    console.log('错误'+errorThrown);
                                }     
                            });
                    });  //编辑提交结束  

            });


        })

 
    }


    // 时间转换
    function getLocalTime(n) {     
       return new Date(n).format('yyyy-MM-dd hh:mm:ss');     
    }

    //处理日期格式
    Date.prototype.format = function(fmt)
    {
      var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt))
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      for(var k in o)
        if(new RegExp("("+ k +")").test(fmt))
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      return fmt;
    }


})

