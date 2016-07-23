
var taskFun;
$(function(){
	
	$(".succ_bn").click(function(){
		var params = {};
		var email = $.trim($("input[name=email]").val());
		if(email.length == 0){
			Message.error({content:"请输入邮箱"});
			return false;
		}
		params.email = email;
		var passwd = $.trim($("input[name=passwd]").val());
		if(passwd.length == 0){
			Message.error({content:"请输入密码"});
			return false;
		}
		params.passwd = passwd;
		var repasswd = $.trim($("input[name=repasswd]").val());
		if(repasswd.length == 0){
			Message.error({content:"请输入确认密码"});
			return false;
		}
		params.repasswd = repasswd;
		if(passwd != repasswd){
			Message.error({content:"两次密码输入不一致"});
			return false;
		}
		
		var emailCode = $.trim($("input[name=emailCode]").val());
		if(emailCode.length == 0){
			Message.error({content:"请输入验证码"});
			return false;
		}
		params.emailCode = emailCode;
		$.ajax({
			url:"/p/user/do_forget_passwd",
			type:"post",
			data:params,
			success: function(data){
				if(data.code == 200){
					location.href = "/p/to_forget_passwd_step2";
				} else{
					Message.error({content:data.message});
				}
			}
		});
		return false;
		
	});
	
	/**
	 * 获取验证码
	 */
	$(".get-email-code").click(function(){
		
		var params = {};
		var email = $("input[name=email]").val();
		if(email.length == 0){
			Message.error({content:"请输入邮箱地址"});
			return false;
		}
		if(email.indexOf("@") == -1){
			Message.error({content:"邮箱不合法"});
			return false;
		}
		params.email = email;
		$.ajax({
			url: "/p/user/get_email_code",
			type: "get",
			data: params,
			success: function(data){
				if(data.code == 200){
					taskFun = setInterval("changeCheckCodeFun()",1000);
				} else{
					Message.error({content:data.message});
				}
			}
		});
	});
	
});

/**
 * 改变验证码
 */
var check_num = 60;
function changeCheckCodeFun(){
	check_num--;
	if(check_num<=0){
		$(".get-email-code").attr("disabled",false);
		clearInterval(taskFun);  
		$(".get-email-code").text("获取邮件验证码");
		check_num = 60;
	}else{
		$(".get-email-code").attr("disabled","disabled");
		$(".get-email-code").text("已发送（"+check_num+"）");
	}
}
