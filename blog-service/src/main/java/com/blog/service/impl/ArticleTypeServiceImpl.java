package com.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.blog.core.entity.ArticleType;
import com.blog.core.service.ArticleTypeService;
import com.blog.service.dao.ArticleTypeDao;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
import com.hecj.common.utils.ResultSupport;
import com.hecj.common.utils.StringUtil;

public class ArticleTypeServiceImpl implements ArticleTypeService{

	private static final Log log = LogFactory.getLog(ArticleTypeService.class);
	
	@Resource
	private ArticleTypeDao articleTypeDao;
	
	@Override
	public Result findArticleTypesByCondition(Map<String, Object> params, Pagination pagination) {
		
		Result result = new ResultSupport();
		try {
			
			Map<String, Object> sqlParams = new HashMap<String,Object>();
			if(!StringUtil.isObjectEmpty(params.get("name"))){
				sqlParams.put("name", params.get("name"));
			}
			if(!StringUtil.isObjectEmpty(params.get("idDelete"))){
				sqlParams.put("idDelete", params.get("idDelete"));
			}
			if(!StringUtil.isObjectNull(params.get("id"))){
				sqlParams.put("id", params.get("id"));
			}
			List<ArticleType> list = articleTypeDao.findArticleTypesByConditions(sqlParams, pagination.getCurrPage(),pagination.getPageSize());
			long total = articleTypeDao.totalArticleTypesByConditions(sqlParams);
			
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
}
