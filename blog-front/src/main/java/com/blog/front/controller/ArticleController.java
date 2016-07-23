package com.blog.front.controller;

import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import com.blog.core.entity.Article;
import com.blog.core.entity.ArticleComment;
import com.blog.core.entity.ArticleContent;
import com.blog.core.entity.User;
import com.blog.core.service.ArticleCommentService;
import com.blog.core.service.ArticleService;
import com.blog.core.service.ArticleTypeService;
import com.blog.core.service.UserService;
import com.blog.front.util.UserUtil;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
import com.hecj.common.utils.ResultJson;
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
	public String index(Long page,Long type,String sq,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
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
	@RequestMapping(value="detail/{articleId}", method=RequestMethod.GET)
	public String detail(@PathVariable Long articleId,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			
			Article article = articleService.findArticleById(articleId);
			model.addAttribute("article", article);
			
			// 文章内容
			List<ArticleContent> articleContentList = articleService.findArticleContentByArticleId(articleId);
			
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
			
			List<ArticleComment> articleCommentList = articleCommentService.findArticleCommentByArticleId(articleId);
			
			model.addAttribute("articleCommentList", articleCommentList);
			
			// 兼容历史转义问题
			if(article.getId()>=71&&article.getId()<=77){
				// 转义
				for(ArticleContent ac : articleContentList){
					ac.setContent(HtmlUtils.htmlEscape(ac.getContent()));
				}
				
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return "article/blog-detail";
	}
	
	/**
	 * 发布帖子
	 */
	@RequestMapping(value="publish", method=RequestMethod.GET)
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
	@ResponseBody
    public ResultJson saveActicle(int permission,String title, String articleList, int type, String AUTH_TOKEN_PUBLISH,HttpServletRequest request,HttpServletResponse response,ModelMap model){
    	
		User user = UserUtil.getUser(request.getSession());
    	long userId = user.getId();
		try {
			
			if(StringUtil.isStrEmpty(title)){
				return new ResultJson(-1l, "请输入标题");
			}
			if(StringUtil.isStrEmpty(articleList)){
				return new ResultJson(-1l, "请输入内容");
			}
			
			// token验证防止恶意刷保存文章
			if(!AUTH_TOKEN_PUBLISH.equals(request.getSession().getAttribute("AUTH_TOKEN_PUBLISH"))){
				return new ResultJson(-1l, "token验证失败");
			}
			request.getSession().removeAttribute("AUTH_TOKEN_PUBLISH");
			
			// 发布文章个数校验，每天最多发表20篇文章
			
			// 文章
			Article article = new Article();
			article.setCommentCount(0);
			article.setRecommend(0);
//			article.setTitle(StringEscapeUtils.escapeHtml(title));
			article.setTitle(HtmlUtils.htmlEscape(title));
			article.setUserId(userId);
			article.setType(type);
			article.setPermission(permission);
			article.setIsDelete(0);
			
			List<ArticleContent> articleContents = new ArrayList<ArticleContent>();
			// 文章内容
			JSONArray arr = new JSONArray(articleList);
			for(int i = 0 ; i<arr.length() ; i++){
				JSONObject json = arr.getJSONObject(i);
				if(StringUtil.isObjectEmpty(json.getString("content"))){
					continue;
				}
				ArticleContent articleContent = new ArticleContent();
				if(json.getInt("type") == 2){
					String images = "";
					String[] imageList = json.getString("content").split(",");
					for(String image : imageList){
						images += image+",";
					}
					articleContent.setContent(HtmlUtils.htmlEscape(images.substring(0, images.length()-1)));
				} else{
					articleContent.setContent(HtmlUtils.htmlEscape(json.getString("content")));
				}
				articleContent.setContentType(json.getInt("type"));
				articleContent.setSort(i);
				articleContents.add(articleContent);
			}
			
			articleService.saveArticle(article, articleContents);
			return new ResultJson(200l, "success");
		} catch (Exception e) {
			log.error(" save article error userId : "+userId);
			e.printStackTrace();
			return new ResultJson(-100000l, e.getMessage());
		}
	}
	
	/**
	 * @功能描述 个人中心-我的随笔
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
		long userId = -1;
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
	 * @功能描述 个人中心-我的随笔-编辑页面
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @Version		V1.0
	 * @date		2016-1-5 下午4:55:04
	 * @author hechaojie
	 * @modify
	 */
	@RequestMapping(value="edit/{articleId}", method=RequestMethod.GET)
	public String edit(@PathVariable Long articleId,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		User user = UserUtil.getUser(request.getSession());
		long userId = user.getId();
		try {
			
			Article article = articleService.findArticleById(articleId);
			model.addAttribute("article", article);
			
			// 文章内容
			List<ArticleContent> articleContentList = articleService.findArticleContentByArticleId(articleId);
			model.addAttribute("articleContentList", articleContentList);
			
			// 查询文章类型
			Pagination articleTypePagination = new Pagination();
			articleTypePagination.setPageSize(100);
			Map<String,Object> articleTypeParams = new HashMap<String,Object>();
			articleTypeParams.put("idDelete", 0);
			Result articleTypeResult = articleTypeService.findArticleTypesByCondition(articleTypeParams, articleTypePagination);
			if(articleTypeResult.isSuccess()){
				model.addAttribute("articleTypeList", articleTypeResult.getData());
			}
		} catch (Exception e) {
			log.error(" edit error userId : "+userId);
			e.printStackTrace();
		}
		return "article/blog-edit";
	}
	
	/**
	 * @功能描述 个人中心-我的随笔-编辑提交
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @Version		V1.0
	 * @date		2016-1-5 下午4:55:04
	 * @author hechaojie
	 * @modify
	 */
	@RequestMapping(value="editActicle", method=RequestMethod.POST)
	@ResponseBody
	public ResultJson editActicle(int permission,Long id, String title, String articleList, int type,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		User user = UserUtil.getUser(request.getSession());
		long userId = user.getId();
		try {
			if(StringUtil.isObjectNull(id)){
				return new ResultJson(-1l, "文章Id为空");
			}
			if(StringUtil.isStrEmpty(title)){
				return new ResultJson(-1l, "请输入标题");
			}
			if(StringUtil.isStrEmpty(articleList)){
				return new ResultJson(-1l, "请输入内容");
			}
			if(StringUtil.isObjectNull(type)){
				return new ResultJson(-3l, "请选择类型");
			}
			
			// 文章
			Article article = articleService.findArticleById(id);
			article.setTitle(HtmlUtils.htmlEscape(title));
			article.setType(type);
			article.setPermission(permission);
			
			// 文章内容
			List<ArticleContent> articleContents = new ArrayList<ArticleContent>();
			JSONArray arr = new JSONArray(articleList);
			for (int i = 0; i < arr.length(); i++) {
				JSONObject json = arr.getJSONObject(i);
				if (StringUtil.isObjectEmpty(json.getString("content"))) {
					continue;
				}
				ArticleContent articleContent = new ArticleContent();
				if (json.getInt("type") == 2) {
					String images = "";
					String[] imageList = json.getString("content").split(",");
					for (String image : imageList) {
						images += image + ",";
					}
					articleContent.setContent(HtmlUtils.htmlEscape(images.substring(0, images.length() - 1)));
				} else {
					articleContent.setContent(HtmlUtils.htmlEscape(json.getString("content")));
				}
				articleContent.setContentType(json.getInt("type"));
				articleContent.setSort(i);
				articleContents.add(articleContent);
			}
			
			articleService.editArticle(article, articleContents);
			
			return new ResultJson(200l, "success");
		} catch (Exception e) {
			log.error(" editActicle error userId : "+userId);
			e.printStackTrace();
			return new ResultJson(-100000l, e.getMessage());
		}
	}
	
}
