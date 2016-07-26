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

});