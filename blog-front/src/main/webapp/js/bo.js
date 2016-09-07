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
			$("._message").text("请输入邮箱地址");
			$("._message").show();
			return false;
		}
		if(email.indexOf("@") == -1){
			$("._message").text("邮箱不合法");
			$("._message").show();
			return false;
		}
		params.email = email;
		$.ajax({
			url: "/sendcode",
			type: "post",
			data: params,
			success: function(data){
				if(data.code == 200){
					$("._message").text("已发送验证码，请登录邮箱查收，10分钟内有效");
					taskFun = setInterval("changeCheckCodeFun()",1000);
					$("._message").show();
				} else{
					$("._message").text(data.message);
					$("._message").show();
				}
			}
		});
	});
	
	// 登录验证码
	$("#logincode").click(function(){
		$(this).attr("src",$(this).attr("src").split("&")[0]+"&_t="+new Date().getTime());
	});

	bo.autoTitleWidth();
	// 动态计算title的长度 
	$(window).resize(function() {
		bo.autoTitleWidth();
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
		$(".send-email-code").attr("disabled",false);
		clearInterval(taskFun);  
		$(".send-email-code").text("获取邮件验证码");
		check_num = 60;
	}else{
		$(".send-email-code").attr("disabled","disabled");
		$(".send-email-code").text("已发送（"+check_num+"）");
	}
}

var bo = bo || {};
// 动态计算标题宽度
bo.autoTitleWidth = function() {
	var w = $('.bo-title').parent().css('width');
	if(!w)return;
	w = w.replace("px", "");
	$('.bo-title').css('width', w - 68 - 25 - 8);
}
