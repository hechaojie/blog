package com.blog.front.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.blog.front.controller.BaseController;
import com.blog.front.util.UserUtil;
import com.blog.core.entity.ArticleComment;
import com.blog.core.entity.User;
import com.blog.core.service.ArticleCommentService;
import com.blog.core.service.ArticleService;
import com.blog.core.service.ArticleTypeService;
import com.blog.core.service.UserService;
import com.hecj.common.utils.ResultJson;

@Controller
public class ArticleCommentController extends BaseController{

	private static final Log log = LogFactory.getLog(ArticleCommentController.class);
	
	@Resource
	public UserService userService;
	
	@Resource
	public ArticleService articleService;

	@Resource
	public ArticleTypeService articleTypeService;

	@Resource
	public ArticleCommentService articleCommentService;
	
	/**
	 * 添加评论
	 */
	@RequestMapping(value="/p/u/article/comment/add", method=RequestMethod.POST)
	@ResponseBody
	public ResultJson add(Long articleId,String content,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		User user = UserUtil.getUser(request.getSession());
    	long userId = user.getId();
    	
    	try {
    		
			if(articleService.findArticleById(articleId) == null){
				return new ResultJson(-1l,"文章不存在");
			}
			
			ArticleComment ac = new ArticleComment();
			ac.setArticleId(articleId);
			ac.setContent(HtmlUtils.htmlEscape(content));
			ac.setCreateAt(System.currentTimeMillis());
			ac.setUserId(userId);
			
			articleCommentService.insert(ac);
			
			return new ResultJson(200l,"success");
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" add article comment userId : "+userId);
			e.printStackTrace();
			return new ResultJson(-100000l, e.getMessage());
		}
    	
	}
	
}
