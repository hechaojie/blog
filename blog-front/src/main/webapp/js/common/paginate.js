/**
 * ========js分页插件========
  使用方法：
  步骤1：
  <ul id="paginate_id" class="pagination" pageNumber="${(page.pageNumber)!}" totalPage="${(page.totalPage)!}" totalRow="${(page.totalRow)!}" params="&"></ul>
  步骤2：
  $(function(){
    	
    	var $paginate = $("#paginate_id");
    	var pageNumber = Number($paginate.attr("pageNumber"));
    	var totalPage = Number($paginate.attr("totalPage"));
    	var totalRow = Number($paginate.attr("totalRow"));
    	
    	var params = $paginate.attr("params");
    	var url = "/";
    	var offsetPage = 3;
    	var cssQuery = "#paginate_id";
    	var config = {
    			url : url,
    			cssQuery : cssQuery,
    			pageNumber : pageNumber,
    			totalRow : totalRow,
    			totalPage : totalPage,
    			offsetPage : offsetPage,
    			pageNumberAlias : 'page',
    			params : params,
    			currCss : 'color: #fff;background-color: #006dcc;'
    	};
    	
    	$.paginate(config);
    });
    简单用法：
    $.paginate({
    	url : "/",
    	cssQuery : "#paginate_id",
    	pageNumber : 1,
    	totalPage : 23,
    	pageNumberAlias : 'page',
    	params : "&name=hello",
    	currCss : 'color: #fff;background-color: #006dcc;'
    })：
 */
$.extend({
	paginate : function(config){
		
		if(!config.offsetPage){
			config.offsetPage = 3;
		}
		
		if(!config.pageNumber){
			console.log("config.pageNumber not found !");
			return ;
		}
		
		if(!config.totalPage){
			console.log("config.totalPage not found !");
			return ;
		}
		
		if(!config.url){
			console.log("config.url not found !");
			return ;
		}
		
		if(!config.cssQuery){
			console.log("config.cssQuery not found !");
			return ;
		}
		
		if(!config.pageNumberAlias){
			config.pageNumberAlias = "page";
		}
		
		if(!config.params){
			config.params = "";
		}
		
    	var text = '';
    	// prev
    	if(config.pageNumber <= 1){
    		text +='<li><a href="javascript:;">上一页</a></li>';
    	}else{
    		text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(config.pageNumber-1)+'"'+config.params+'>上一页</a></li>';
    	}
    	
    	// left
		if(config.pageNumber - config.offsetPage > 1 ){
			text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'=1">1</a></li>';
			if(config.pageNumber - config.offsetPage != 2){
				text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'=1'+config.params+'">...</a></li>';
			}
			for(var i=config.offsetPage ;i>=1 ; i--){
				text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(config.pageNumber-i)+''+config.params+'">'+(config.pageNumber-i)+'</a></li>';
			}
		} else{
			for(var i=1; i< config.pageNumber ;i++){
				text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+i+''+config.params+'">'+i+'</a></li>';
			}
		} 	
    	
    	// curr page
		text +='<li><a style="'+config.currCss+'" href="'+config.url+'?'+config.pageNumberAlias+'='+config.pageNumber+''+config.params+'">'+config.pageNumber+'</a></li>';
		
		// right
		if(config.pageNumber + config.offsetPage < config.totalPage ){
			for(var i=1 ; i<= config.offsetPage ; i++){
				text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(config.pageNumber+i)+''+config.params+'">'+(config.pageNumber+i)+'</a></li>';
			}
			if(config.pageNumber + config.offsetPage != config.totalPage-1){
				text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(config.totalPage)+''+config.params+'">...</a></li>';
			}
			text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(config.totalPage)+''+config.params+'">'+(config.totalPage)+'</a></li>';
		} else{
			for(var i=config.pageNumber+1; i<= config.totalPage ;i++){
				text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(i)+''+config.params+'">'+(i)+'</a></li>';
			}
		} 	
    	
		// next
    	if(config.pageNumber >= config.totalPage){
    		text +='<li><a href="javascript:;">下一页</a></li>';
    	} else{
    		text +='<li><a href="'+config.url+'?'+config.pageNumberAlias+'='+(config.pageNumber+1)+''+config.params+'">下一页</a></li>';
    	}
         
    	$(config.cssQuery).html(text);
	}
});

