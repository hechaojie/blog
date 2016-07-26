package com.blog.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.ArticleComment;

public interface ArticleCommentDao {

	public List<ArticleComment> findArticleCommentByArticleId(@Param("articleId")long articleId);
	
	public long insert(ArticleComment ac);
	
}
