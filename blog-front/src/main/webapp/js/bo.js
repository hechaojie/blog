$(function() {
	
	/**
	 * 回车事件
	 */
	document.onkeydown = function(e){ 
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			$("div.succ_bn").click();
		}
	}
	
	/**
	 * 获取验证码
	 */
	$(".send-email-code").click(function(){
		
		var params = {};
		var email = $("input[name=email]").val();
		if(email.length == 0){
			$(".bo-error-message").text("请输入邮箱地址");
			return false;
		}
		if(email.indexOf("@") == -1){
			$(".bo-error-message").text("邮箱不合法");
			return false;
		}
		params.email = email;
		$.ajax({
			url: "/sendcode",
			type: "post",
			data: params,
			success: function(data){
				if(data.code == 200){
					$(".bo-error-message").text("已发送验证码，请登录邮箱查收，10分钟内有效");
					taskFun = setInterval("changeCheckCodeFun()",1000);
				} else{
					$(".bo-error-message").text(data.message);
				}
			}
		});
	});

});



/**
 * 改变验证码
 */
var taskFun;
var check_num = 60*10;
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
