package com.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.blog.core.entity.Article;
import com.blog.core.entity.ArticleContent;
import com.blog.core.service.ArticleService;
import com.blog.core.vo.ArticleVo;
import com.blog.service.dao.ArticleContentDao;
import com.blog.service.dao.ArticleDao;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
import com.hecj.common.utils.ResultSupport;
import com.hecj.common.utils.StringUtil;

public class ArticleServiceImpl implements ArticleService{

	private static final Log log = LogFactory.getLog(ArticleServiceImpl.class);
	
	@Resource
	private ArticleDao articleDao;
	
	@Resource
	private ArticleContentDao articleContentDao;
	
	@Override
	public Result findArticlesByCondition(Map<String, Object> params, Pagination pagination) {
		
		Result result = new ResultSupport();
		try {
			
			Map<String, Object> sqlParams = new HashMap<String,Object>();
			if(!StringUtil.isObjectEmpty(params.get("title"))){
				sqlParams.put("title", "%"+params.get("title")+"%");
			}
			if(!StringUtil.isObjectNull(params.get("type"))){
				sqlParams.put("type", params.get("type"));
			}
			if(!StringUtil.isObjectNull(params.get("userId"))){
				sqlParams.put("userId", params.get("userId"));
			}
			if(!StringUtil.isObjectNull(params.get("permission"))){
				sqlParams.put("permission", params.get("permission"));
			}
			if(!StringUtil.isObjectNull(params.get("isDelete"))){
				sqlParams.put("isDelete", params.get("isDelete"));
			}
			List<ArticleVo> list = articleDao.findArticlesByConditions(sqlParams, pagination.getCurrPage(),pagination.getPageSize());
			long total = articleDao.totalArticlesByConditions(sqlParams);
			
			pagination.setCountSize(total);
			result.setData(list);
			result.setPagination(pagination);
			result.setResult(true);
		} catch (Exception e) {
			log.error(" params {} "+ params);
			e.printStackTrace();
			result.setResult(false);
		}
		return result;
	}

	@Override
	public Article findArticleById(long articleId) {
		return articleDao.findArticleById(articleId);
	}

	@Override
	public List<ArticleContent> findArticleContentByArticleId(long articleId) {
		return articleContentDao.findArticleContentByArticleId(articleId);
	}

	@Override
	public boolean saveArticle(Article article, List<ArticleContent> articleContents) {
		try {
			article.setCreateAt(System.currentTimeMillis());
			article.setUpdateAt(System.currentTimeMillis());
			articleDao.save(article);
			for(ArticleContent articleContent : articleContents){
				articleContent.setArticleId(article.getId());
				articleContent.setCreateAt(System.currentTimeMillis());
				articleContent.setUpdateAt(System.currentTimeMillis());
				articleContentDao.save(articleContent);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean editArticle(Article article, List<ArticleContent> articleContents) {
		try {
			// 编辑文章
			articleDao.update(article);
			
			// 删除文章内容
			articleContentDao.deleteContent(article.getId());
			
			// 插入文章内容
			for(ArticleContent articleContent : articleContents){
				articleContent.setArticleId(article.getId());
				articleContentDao.save(articleContent);
			}
			return true;
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
