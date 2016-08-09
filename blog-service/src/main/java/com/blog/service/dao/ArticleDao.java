package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.service.core.entity.Article;
import com.blog.service.core.vo.ArticleVo;

public interface ArticleDao {

	public List<ArticleVo> findArticlesByConditions(@Param("sqlParams")Map<String,Object> sqlParams,@Param("start")long start,@Param("size")int size);

	public long totalArticlesByConditions(@Param("sqlParams")Map<String,Object> sqlParams);
	
	/**
	 * 根据文章Id查询
	 */
	public Article findArticleById(String id);
	
	/**
	 * @功能描述 保存文章
	 * @param article
	 * @return long
	 * @Version		V1.0
	 * @date		2016-1-5 上午11:49:28
	 * @author hechaojie
	 * @modify
	 */
	public long save(Article article);
	
	/**
	 * @功能描述 编辑文章
	 * @param article
	 * @return long
	 * @Version		V1.0
	 * @date		2016-1-5 16:49:28
	 * @author hechaojie
	 * @modify
	 */
	public int update(Article article);
}
