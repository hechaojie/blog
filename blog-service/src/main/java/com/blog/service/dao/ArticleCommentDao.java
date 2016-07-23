package com.blog.service.dao;

import java.util.List;

import com.blog.core.entity.ArticleComment;

public interface ArticleCommentDao {

	public List<ArticleComment> findArticleCommentByArticleId(long articleId);
	
	public long insert(ArticleComment ac);
	
}
