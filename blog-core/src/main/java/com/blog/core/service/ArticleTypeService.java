package com.blog.core.service;

import java.util.Map;

import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;

public interface ArticleTypeService {

	public Result findArticleTypesByCondition(Map<String, Object> params, Pagination pagination) ;
}
