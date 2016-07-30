package com.blog.front.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.HtmlUtils;

import com.blog.core.entity.Article;
import com.blog.core.entity.ArticleContent;
import com.blog.core.entity.User;
import com.blog.core.service.ArticleCommentService;
import com.blog.core.service.ArticleService;
import com.blog.core.service.ArticleTypeService;
import com.blog.core.service.UserService;
import com.blog.front.constant.ConfigProvider;
import com.blog.front.util.UserUtil;
import com.hecj.common.utils.DateFormatUtil;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
import com.hecj.common.utils.StringUtil;

@Controller
@RequestMapping(value="/article")
public class ArticleController extends BaseController{

	private static final Log log = LogFactory.getLog(ArticleController.class);
	
	@Resource
	public UserService userService;
	
	@Resource
	public ArticleService articleService;

	@Resource
	public ArticleTypeService articleTypeService;

	@Resource
	public ArticleCommentService articleCommentService;
	
	/**
	 * 文章列表模块
	 */
	@RequestMapping(value="", method=RequestMethod.GET)
	public String index(Long page,String type,String sq,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		if(page == null){
			page = 1l;
		}
		sq = StringEscapeUtils.escapeHtml(sq);
		// 查询文章类型
		Pagination articleTypePagination = new Pagination();
		articleTypePagination.setPageSize(100);
		Map<String,Object> articleTypeParams = new HashMap<String,Object>();
		articleTypeParams.put("idDelete", 0);
		Result articleTypeResult = articleTypeService.findArticleTypesByCondition(articleTypeParams, articleTypePagination);
		if(articleTypeResult.isSuccess()){
			model.addAttribute("articleTypeList", articleTypeResult.getData());
		}
		
		// 查询文章
		Pagination articlePagination = new Pagination();
		articlePagination.setCurrPage(page);
		Map<String,Object> articleParams = new HashMap<String,Object>();
		if(!StringUtil.isObjectNull(type)){
			articleParams.put("type", type);
		}
		if(!StringUtil.isStrEmpty(sq)){
			articleParams.put("title", sq);
		}
		articleParams.put("permission", 0);
		articleParams.put("isDelete", 0);
		Result articleResult= articleService.findArticlesByCondition(articleParams, articlePagination);
		if(articleTypeResult.isSuccess()){
			model.addAttribute("articleResult", articleResult);
		}
		
		model.addAttribute("type", type);
		model.addAttribute("search_content", sq);
		
		return "article/index";
	}
	
	/**
	 * 文章详情
	 */
	@RequestMapping(value="detail/{year}/{month}/{day}/{endId}", method=RequestMethod.GET)
	public String detail(@PathVariable String year,@PathVariable String month,@PathVariable String day,@PathVariable String endId,Long page,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			
			if(page == null){
				page =1l;
			}
			
			Article article = articleService.findArticleById(year+month+day+endId);
			model.addAttribute("article", article);
			
			// 文章内容
			List<ArticleContent> articleContentList = articleService.findArticleContentByArticleId(article.getId());
			
			model.addAttribute("articleContentList", articleContentList);
			
			User author = userService.findUserById(article.getUserId());
			model.addAttribute("author", author);
			
			// 查询文章类型
			Pagination articleTypePagination = new Pagination();
			articleTypePagination.setPageSize(100);
			Map<String,Object> articleTypeParams = new HashMap<String,Object>();
			articleTypeParams.put("idDelete", 0);
			Result articleTypeResult = articleTypeService.findArticleTypesByCondition(articleTypeParams, articleTypePagination);
			if(articleTypeResult.isSuccess()){
				model.addAttribute("articleTypeList", articleTypeResult.getData());
			}
			
			Pagination pg = new Pagination();
			pg.setCurrPage(page);
			pg.setPageSize(20);
			Result commentResult = articleCommentService.findArticleCommentByArticleId(article.getId(),pg);
			
			model.addAttribute("commentResult", commentResult);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "article/detail";
	}
	
	/**
	 * 发布帖子
	 */
	@RequestMapping(value="publish")
	public String publish(HttpServletRequest request,HttpServletResponse response,ModelMap mode){
		try {
			
			String AUTH_TOKEN_PUBLISH = UUID.randomUUID().toString();
			request.getSession().setAttribute("AUTH_TOKEN_PUBLISH", AUTH_TOKEN_PUBLISH);
			request.setAttribute("AUTH_TOKEN_PUBLISH", AUTH_TOKEN_PUBLISH);
			// 查询文章类型
			Pagination articleTypePagination = new Pagination();
			articleTypePagination.setPageSize(100);
			Map<String,Object> articleTypeParams = new HashMap<String,Object>();
			articleTypeParams.put("idDelete", 0);
			Result articleTypeResult = articleTypeService.findArticleTypesByCondition(articleTypeParams, articleTypePagination);
			if(articleTypeResult.isSuccess()){
				mode.addAttribute("articleTypeList", articleTypeResult.getData());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "article/publish";
	}
	
	/**
	 * @功能描述 发布帖子-提交
	 * @param title 文章标题
	 * @param articleList 文章内容集合JSON格式字符串
	 * @param type 文章类型
	 * @return ResultJson
	 * @Version		V1.0
	 * @date		2016-1-5 11:18:07
	 * @author hecj
	 * @modify
	 */
	@RequestMapping(value="saveActicle", method=RequestMethod.POST)
    public String saveActicle(String title, String content, String type,int permission, String AUTH_TOKEN_PUBLISH,
    		HttpServletRequest request,HttpServletResponse response,ModelMap model)throws Exception{
    	
		User user = UserUtil.getUser(request.getSession());
		String userId = "-1";
		try {
			
			if(StringUtil.isStrEmpty(title)){
				model.addAttribute("error", "请输入标题");
				return "forward:/article/publish";
			}
			if(StringUtil.isStrEmpty(content)){
				model.addAttribute("error", "请输入正文");
				return "forward:/article/publish";
			}
			
			userId = user.getId();
			
			// token验证防止恶意刷保存文章
			if(!AUTH_TOKEN_PUBLISH.equals(request.getSession().getAttribute("AUTH_TOKEN_PUBLISH"))){
				model.addAttribute("error", "提交错误，请刷新页面后重试");
				return "forward:/article/publish";
			}
			request.getSession().removeAttribute("AUTH_TOKEN_PUBLISH");
			
			// 发布文章个数校验，每天最多发表20篇文章
			Pagination pagination = new Pagination();
			pagination.setCurrPage(1l);
			pagination.setPageSize(Integer.MAX_VALUE);
			// 查询我发布的文章
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", userId);
			params.put("startTime", DateFormatUtil.getDayBegin(new Date()).getTime());
			params.put("endTime", DateFormatUtil.getDayEnd(new Date()).getTime());
			Result result = articleService.findArticlesByCondition(params, pagination);
			if(result.getPagination().getCountSize()>=ConfigProvider.publish_article_max_num){
				model.addAttribute("error", "当天发布文章数："+result.getPagination().getCountSize()+"，休息一下，过一会后再来吧。");
				return "forward:/article/publish";
			}
			
			// 文章主体
			Article article = new Article();
			article.setCommentCount(0);
			article.setRecommend(0);
			article.setTitle(HtmlUtils.htmlEscape(title));
			article.setUserId(userId);
			article.setType(type);
			article.setPermission(permission);
			article.setIsDelete(0);
			
			// 正文
			List<ArticleContent> articleContents = new ArrayList<ArticleContent>();
			ArticleContent ac = new ArticleContent();
			ac.setContent(content);
			articleContents.add(ac);
			
			articleService.saveArticle(article, articleContents);
			
			return "redirect:/article";
		} catch (Exception e) {
			log.error(" save article error userId : "+userId);
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * @功能描述 个人中心
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @Version		V1.0
	 * @date		2016-1-5 下午4:55:04
	 * @author hechaojie
	 * @modify
	 */
	@RequestMapping(value="myarticle", method=RequestMethod.GET)
	public String myArticle(Long page,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		if(page == null){
			page = 1l;
		}
		String userId = "-1";
		try {
			User user = UserUtil.getUser(request.getSession());
	    	userId = user.getId();
			Pagination pagination = new Pagination();
			pagination.setCurrPage(page);
			
			// 查询我发布的文章
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("userId", userId);
			Result result = articleService.findArticlesByCondition(params, pagination);
			if(result.isSuccess()){
				model.addAttribute("articleResult", result);
			}
			
		} catch (Exception e) {
			log.error(" myArticle error userId : "+userId);
			e.printStackTrace();
		}
		return "user/index";
	}
	
	/**
	 * @功能描述 个人中心-编辑页面
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @Version		V1.0
	 * @date		2016-1-5 下午4:55:04
	 * @author hechaojie
	 * @modify
	 */
	@RequestMapping(value="edit/{year}/{month}/{day}/{endId}")
	public String edit(@PathVariable String year,@PathVariable String month,@PathVariable String day,@PathVariable String endId,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		String articleId = year+month+day+endId;
		User user = UserUtil.getUser(request.getSession());
		String userId = user.getId();
		try {
			
			Article article = articleService.findArticleById(articleId);
			model.addAttribute("article", article);
			
			// 文章内容
			List<ArticleContent> articleContentList = articleService.findArticleContentByArticleId(articleId);
			model.addAttribute("articleContent", articleContentList.get(0));
			
			// 查询文章类型
			Pagination articleTypePagination = new Pagination();
			articleTypePagination.setPageSize(100);
			Map<String, Object> articleTypeParams = new HashMap<String, Object>();
			articleTypeParams.put("idDelete", 0);
			Result articleTypeResult = articleTypeService.findArticleTypesByCondition(articleTypeParams,
					articleTypePagination);
			if (articleTypeResult.isSuccess()) {
				model.addAttribute("articleTypeList", articleTypeResult.getData());
			}
						
		} catch (Exception e) {
			log.error(" edit error userId : "+userId);
			e.printStackTrace();
		}
		return "article/edit";
	}
	
	/**
	 * @功能描述 个人中心-编辑提交
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @Version		V1.0
	 * @date		2016-1-5 下午4:55:04
	 * @author hechaojie
	 * @throws Exception 
	 * @modify
	 */
	@RequestMapping(value="editActicle", method=RequestMethod.POST)
	public String editActicle(int permission,String id, String title, String content, String type,HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		User user = UserUtil.getUser(request.getSession());
		String userId = user.getId();
		try {
			if(StringUtil.isStrEmpty(title)){
				model.addAttribute("error", "请输入标题");
				return "forward:/article/edit/"+getArticleDetialURI(id);
			}
			if(StringUtil.isStrEmpty(content)){
				model.addAttribute("error", "请输入正文");
				return "forward:/article/edit/"+getArticleDetialURI(id);
			}
			// 文章
			Article article = articleService.findArticleById(id);
			article.setTitle(HtmlUtils.htmlEscape(title));
			article.setType(type);
			article.setPermission(permission);
			
			// 正文
			List<ArticleContent> articleContents = new ArrayList<ArticleContent>();
			ArticleContent ac = new ArticleContent();
			ac.setContent(content);
			articleContents.add(ac);
			
			articleService.editArticle(article, articleContents);
			
			return "redirect:/article/detail/"+getArticleDetialURI(id);
		} catch (Exception e) {
			log.error(" editActicle error userId : "+userId);
			e.printStackTrace();
			throw e;
		}
	}
	
}
