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

	$("div.succ_bn").click(function() {
		var email = $("input[name=email]").val();
		var password = $("input[name=password]").val();
		var back_url = $("input[name=back_url]").val();
		
		var email = $("input[name=email]").val();
    	if($.trim(email).length == 0){
    		Message.error({content:"请输入用户名"});
    		return false;
    	}
		
		if(password.length == 0){
			Message.error({content:"请输入密码"});
			return false;
		}

		var params = {};
		params.email = email;
		params.password = password;

		$.ajax({
			type : "POST",
			url : "/dologin",
			data : params,
			success : function(data) {
				if (data.code == 200) {
					if(back_url.length == 0){
						location.href = "/";
					}else{
						location.href = back_url;
					}
				} else {
					Message.error({content:data.message});
				}
			}
		});
	});

});