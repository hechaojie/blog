package com.blog.service.dao.impl;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.core.entity.ArticleContent;
import com.blog.service.dao.ArticleContentDao;

public class ArticleContentDaoImpl extends SqlSessionDaoSupport implements ArticleContentDao {

	@Override
	public List<ArticleContent> findArticleContentByArticleId(long articleId) {

		return this.getSqlSession().selectList("articleContent.findArticleContentByArticleId", articleId);
	}

	@Override
	public long save(ArticleContent articleContent) {
		articleContent.setCreateAt(System.currentTimeMillis());
		articleContent.setUpdateAt(System.currentTimeMillis());
		
		int rows = this.getSqlSession().insert("articleContent.save", articleContent);
		return rows > 0 ? articleContent.getId() : -1;
	}

	@Override
	public int deleteContent(long articleId) {
		return this.getSqlSession().delete("articleContent.deleteContent", articleId);
	}

}
