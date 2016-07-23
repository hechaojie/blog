package com.blog.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.blog.core.entity.ArticleComment;
import com.blog.core.service.ArticleCommentService;
import com.blog.service.dao.ArticleCommentDao;

public class ArticleCommentServiceImpl implements ArticleCommentService {

	@Resource
	public ArticleCommentDao articleCommentDao;
	
	@Override
	public List<ArticleComment> findArticleCommentByArticleId(long articleId) {
		
		List<ArticleComment> list = articleCommentDao.findArticleCommentByArticleId(articleId);
		return list;
	}

	@Override
	public long insert(ArticleComment ac) {
		return articleCommentDao.insert(ac);
	}

}
