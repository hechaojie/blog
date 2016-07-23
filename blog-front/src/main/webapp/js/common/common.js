/**
 * by HECJ
 */
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		if (XMLHttpRequest.status == 999) {
			location.href = "/login?b="+encodeURIComponent(window.location.href);
		}
	}
});

$(function(){
	
	/*搜索*/
	$("button[type=submit]").click(function(){
		var form_control = $("input[class=form-control]").val();
		if(form_control.length == 0){
			$("input[class=form-control]").focus();
			return false;
		}
		location.href = "/article?sq="+encodeURIComponent(form_control);
		return false;
	});
})


/**
 * 对placeholder的支持
 */
function isPlaceholder(){
 var input = document.createElement('input');
 return 'placeholder' in input;
}
if (!isPlaceholder()) {//不支持placeholder 用jquery来完成
 $(document).ready(function() {
     if(!isPlaceholder()){
         $("input").not("input[type='password']").each(//把input绑定事件 排除password框
             function(){
                 if($(this).val()=="" && $(this).attr("placeholder")!=""){
                     $(this).val($(this).attr("placeholder"));
                     $(this).focus(function(){
                         if($(this).val()==$(this).attr("placeholder")) $(this).val("");
                     });
                     $(this).blur(function(){
                         if($(this).val()=="") $(this).val($(this).attr("placeholder"));
                     });
                 }
         });
         //对password框的特殊处理1.创建一个text框 2获取焦点和失去焦点的时候切换
         var pwdField = $("input[type=password]");
         var pwdVal  = pwdField.attr('placeholder');
         pwdField.after('<input id="pwdPlaceholder" type="text" value='+pwdVal+' autocomplete="off" />');
         var pwdPlaceholder = $('#pwdPlaceholder');
         pwdPlaceholder.show();
         pwdField.hide();
         
         pwdPlaceholder.focus(function(){
        	 pwdPlaceholder.hide();
        	 pwdField.show();
        	 pwdField.focus();
         });
         
         pwdField.blur(function(){
	          if(pwdField.val() == '') {
	        	  pwdPlaceholder.show();
	        	  pwdField.hide();
	          }
         });
     }
 });
}