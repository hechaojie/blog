package com.blog.service;

import java.util.List;
import java.util.Map;

import com.blog.service.core.entity.ArticleComment;
import com.blog.service.core.entity.ArticleCommentReply;
import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;

public interface ArticleCommentService {

	/**
	 * 描述：查询文章评论
	 * @author: hecj
	 */
	public Result findByConditions(Map<String,Object> p,Pagination pg);
	
	/**
	 * 描述：保存文章评论
	 * @author: hecj
	 */
	public long insertArticleComment(ArticleComment ac);
	
	/**
	 * 描述：查询评论回复
	 * @author: hecj
	 */
	public List<ArticleCommentReply> findArticleCommentReplyByCommentId(String commentId);
	
	/**
	 * 描述：保存评论回复
	 * @author: hecj
	 */
	public long insertArticleCommentReply(ArticleCommentReply acr);
	
}
