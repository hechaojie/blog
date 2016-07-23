$(document).ready(function() {
	

		   $(".lab_model_03 .lab_03").hover(function() {
		   	   $(this).addClass('cur').siblings().removeClass('cur');

		   });
		   
		   // 体检
		   var isSub = false;
		   $(".succ_bn").click(function(){
			   if(isSub){
				   Message.error({content:"请不要重复提交"});
				   return false;
			   }
			   var youremail = $("input[name=youremail]").val();
			   
			   var params = {};
			   params.email = youremail;
			   
			   if(!CheckMail(youremail)){
				   Message.error({content:"邮箱格式不正确"});
				   return false;
			   }
			   
			   if(youremail.indexOf("@") == -1){
				   Message.error({content:"邮箱格式不正确"});
				   return false;
			   }
			   
			   $.ajax({
					type : "POST",
					url : "/p/user/sendMailReg",
					data : params,
					success : function(data) {
						if(data.code == 200){
							$("#divmail").html("<font color='green'>已发送邮件，请登录您的邮箱设置密码，有效期为24小时。</font>");
							$(".succ_bn").html("<a href='#'>注册中</a>");
							isSub = true;
						} else{
							 Message.error({content:data.message});
						}
					}
				});
			   
		   });
		   
});


function CheckMail(mail) {
	var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (filter.test(mail))
		return true;
	else {
		return false;
	}
}
