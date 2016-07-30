package com.blog.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.ArticleContent;

public interface ArticleContentDao {

	public List<ArticleContent> findArticleContentByArticleId(@Param("articleId")String articleId);
	
	/**
	 * @功能描述 保存文章内容
	 * @param articleContent
	 * @return long
	 * @Version		V1.0
	 * @date		2016-1-5 下午12:15:19
	 * @author hechaojie
	 * @modify
	 */
	public long save(ArticleContent articleContent);
	
	/**
	 * @功能描述 删除文章内容
	 * @param articleId
	 * @return int
	 * @Version		V1.0
	 * @date		2016-1-5 下午6:17:00
	 * @author hechaojie
	 * @modify
	 */
	public int deleteContent(@Param("articleId")String articleId);
}
