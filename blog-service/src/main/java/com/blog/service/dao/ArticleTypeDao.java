package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.ArticleType;

public interface ArticleTypeDao {

	public List<ArticleType> findArticleTypesByConditions(@Param("sqlParams")Map<String,Object> sqlParams,@Param("start")long start,@Param("size")int size);

	public long totalArticleTypesByConditions(Map<String,Object> sqlParams);
}
