package com.blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.blog.core.entity.ArticleComment;
import com.blog.core.entity.ArticleCommentReply;
import com.blog.core.service.ArticleCommentService;
import com.blog.core.vo.ArticleCommentVo;
import com.blog.service.dao.ArticleCommentDao;
import com.blog.service.dao.ArticleCommentReplyDao;
import com.hecj.common.utils.GenerateUtil;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
import com.hecj.common.utils.ResultSupport;

public class ArticleCommentServiceImpl implements ArticleCommentService {

	@Resource
	public ArticleCommentDao articleCommentDao;
	
	@Resource
	public ArticleCommentReplyDao articleCommentReplyDao;
	
	@Override
	public Result findArticleCommentByArticleId(String articleId,Pagination pg) {
		Result result = new ResultSupport();
		List<ArticleCommentVo> list = articleCommentDao.findByArticleId(articleId,pg.getStartCursor(),pg.getPageSize());
		long total = articleCommentDao.countByArticleId(articleId);
		result.setData(list);
		pg.setCountSize(total);
		result.setPagination(pg);
		return result;
	}

	@Override
	public long insertArticleComment(ArticleComment ac) {
		ac.setId(GenerateUtil.generateId());
		ac.setCreateAt(System.currentTimeMillis());
		return articleCommentDao.insert(ac);
	}

	@Override
	public List<ArticleCommentReply> findArticleCommentReplyByCommentId(String commentId) {
		List<ArticleCommentReply> list = articleCommentReplyDao.findByCommentId(commentId);
		return list;
	}

	@Override
	public long insertArticleCommentReply(ArticleCommentReply acr) {
		acr.setId(GenerateUtil.generateId());
		acr.setCreateAt(System.currentTimeMillis());
		articleCommentReplyDao.insert(acr);
		return 0;
	}

}
