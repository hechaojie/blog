package com.blog.front.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.front.constant.ConfigProvider;
import com.blog.front.util.UserUtil;
import com.blog.service.ArticleCommentService;
import com.blog.service.ArticleService;
import com.blog.service.ArticleTypeService;
import com.blog.service.UserService;
import com.blog.service.core.entity.ArticleComment;
import com.blog.service.core.entity.User;
import com.hecj.common.util.date.DateFormatUtil;
import com.hecj.common.util.result.Pagination;

@Controller
public class ArticleCommentController extends BaseController {

	private static final Log log = LogFactory.getLog(ArticleCommentController.class);

	@Resource
	public UserService userService;

	@Resource
	public ArticleService articleService;

	@Resource
	public ArticleTypeService articleTypeService;

	@Resource
	public ArticleCommentService articleCommentService;
	
	@Resource
	public UserUtil userUtil;

	/**
	 * 描述：添加评论
	 * 
	 * @author: hecj
	 */
	@RequestMapping(value = "/article/comment/add", method = RequestMethod.POST)
	public String add(String articleId, String content, HttpServletRequest request, HttpServletResponse response,
			ModelMap model) {

		User user = userUtil.getUser(request.getSession());
		if(user == null){
			setMessage(request, -1, "您还没有登录，登录后再来评论吧");
			return "common/_message";
		}
		String userId = user.getId();
		try {
			if (articleService.findArticleById(articleId) == null) {
				setMessage(request, -1, "您要评论的文章丢失了，请核实后提交");
				return "common/_message";
			}
			
			// 校验每天评论上线
			Map<String,Object> p = new HashMap<String,Object>();
			p.put("startTime", DateFormatUtil.getDayBegin(new Date()).getTime());
			p.put("endTime", DateFormatUtil.getDayEnd(new Date()).getTime());
			p.put("userId", userId);
			long total = articleCommentService.findByConditions(p, new Pagination(1)).getPagination().getCountSize();
			if (total >= ConfigProvider.publish_article_comment_max_num) {
				setMessage(request, -1, "您的评论太频繁了，休息一下再来评论吧");
				return "common/_message";
			}
			
			ArticleComment ac = new ArticleComment();
			ac.setArticleId(articleId);
			ac.setContent(content);
			ac.setUserId(userId);
			articleCommentService.insertArticleComment(ac);
			
			return "redirect:/article/detail/"+getArticleDetialURI(articleId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" add article comment userId : " + userId);
			e.printStackTrace();
			setMessage(request, -1, "发布评论超时，请稍后再试");
			return "common/_message";
		}
	}

}
