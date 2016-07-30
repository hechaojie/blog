package com.blog.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.ArticleCommentReply;

public interface ArticleCommentReplyDao {

	public List<ArticleCommentReply> findByCommentId(@Param("commentId")String commentId);
	
	public long insert(ArticleCommentReply acr);
	
}
