$(function(){
    $(".leftNav").css("height", $(window).height());
    var getAdministratorInfo = "http://localhost:8080/administrator/getAdministratorInfo";
    var editAsk = "http://localhost:8080/administrator/updateAdministratorInfo";
    var editPassword ="http://localhost:8080/administrator/updatePassword";
    // 获取信息
    $.ajax({
                url: getAdministratorInfo,
                dataType:"json",
                contentType: "application/json; charset=UTF-8",
                type: "get",
                success:function(result)
                {
                 
                    if(result.status == 0)
                    {
                         initThePage(result.data);                  
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
    // ----------函数---------------
    // 初始化
    function initThePage(results){
        $(".infoPart").find("tbody").append(
            "<tr class='info'><td align='center'>"+results.id+"</td><td align='center'>"+results.name+"</td><td align='center'>"+results.sex+"</td><td align='center'>"+results.age+"</td><td align='center'>"+results.title+"</td><td align='center'>"+getLocalTime(results.admissionTime).substring(0,10)+"</td><td align='center'><button type='button' class='btn btn-success btnEdit' data-toggle='modal' data-target='#see'><span class='glyphicon glyphicon-search' aria-hidden='true'></span> 查看详情</button> <button type='button' class='btn btn-warning' data-toggle='modal' data-target='#editPassword' id='clickEditPassword'><span class='glyphicon glyphicon-edit' aria-hidden='true'></span> 修改密码</button> </td></tr>");

        // 查看并修改
        $(".editId").val(results.id);
        $(".editName").val(results.name);
        // $(".editPassword").val(results.password);
        $(".editSex").val(results.sex);
        $(".editAge").val(results.age);
        $(".editTitle").val(results.title);
        $(".editAdmissionTime").val(getLocalTime(results.admissionTime).substring(0,10));
        $("#editSub").on("click", function(){
            var editData ={
                "id":                         $(".editId").val(),
                "name":                       $(".editName").val(),
                "sex":                        $(".editSex").val(),
                "age":                        $(".editAge").val(),
                "title":                      $(".editTitle").val(),
                "admissionTime":              $(".editAdmissionTime").val(),
            };
            // 修改信息
            $.ajax({
                    url: editAsk,
                    dataType:"json",
                    type:"POST",
                    data:JSON.stringify(editData),
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

            
        });
        $("#clickEditPassword").on("click", function(){
            $(".oldPasswordTip").html('');
            $(".newPasswordTip").html('');
            $(".newPassword1Tip").html('');

        });


        // 提交密码
        $("#subPassword").on("click", function(){
            var passed =[false,false];
            if($(".oldPassword").val() == results.password){
                $(".oldPasswordTip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>与旧密码一致</span>");
                passed[0]=true;
            }
            else{
                $(".oldPasswordTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>密码验证错误</span>"); 
                passed[0]=false;
            }
           if($(".newPassword").val() == $(".newPassword1").val()){
                $(".newPassword1Tip").html("<span style='color:green'><span class='glyphicon glyphicon-ok'></span>两次输入一致</span>");
                passed[1]=true;
            }
            else{
                $(".newPassword1Tip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>两次输入不一致</span>"); 
                passed[1]=false;
            }
            var sendPsw ={
                "password":$(".newPassword").val()
            }
            if(passed[0]&&passed[1]){
                $.ajax({
                    url: editPassword,
                    data: $.param(sendPsw) ,
                    dataType:"json",
                    contentType: "application/x-www-form-urlencoded;charset=utf-8",
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
                $(".oldPassword").val('');
                $(".newPassword").val('');
                $(".newPassword1").val('');
                $(".subTip").html("<span style='color:red'><span class='glyphicon glyphicon-remove'></span>验证不通过</span>");
            }
           
        });

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