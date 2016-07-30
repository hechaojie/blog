package com.blog.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.ArticleComment;
import com.blog.core.vo.ArticleCommentVo;

public interface ArticleCommentDao {

	public List<ArticleCommentVo> findByArticleId(@Param("articleId")String articleId,@Param("start")long start,@Param("size")int size);
	
	public long countByArticleId(@Param("articleId")String articleId);
	
	public long insert(ArticleComment ac);
	
}
