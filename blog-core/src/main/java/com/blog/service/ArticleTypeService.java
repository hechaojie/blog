package com.blog.service;

import java.util.Map;

import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;

public interface ArticleTypeService {

	public Result findArticleTypesByCondition(Map<String, Object> params, Pagination pagination) ;
}
