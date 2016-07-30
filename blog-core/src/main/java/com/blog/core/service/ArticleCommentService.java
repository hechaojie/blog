package com.blog.core.service;

import java.util.List;

import com.blog.core.entity.ArticleComment;
import com.blog.core.entity.ArticleCommentReply;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;

public interface ArticleCommentService {

	/**
	 * 描述：查询文章评论
	 * @author: hecj
	 */
	public Result findArticleCommentByArticleId(String articleId,Pagination pg);
	
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
