package com.blog.service.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.core.entity.Article;
import com.blog.core.vo.ArticleVo;
import com.blog.service.dao.ArticleDao;

public class ArticleDaoImpl extends SqlSessionDaoSupport implements ArticleDao {

	@Override
	public List<ArticleVo> findArticlesByConditions(Map<String, Object> params, long currPage, int pageSize) {
		
		params.put("start", pageSize * (currPage-1));
		params.put("offset", pageSize);
		
		SqlSession session = this.getSqlSession();
		List<ArticleVo> articles = session.selectList("article.findArticlesByConditions", params);
		return articles;
	}

	@Override
	public long totalArticlesByConditions(Map<String, Object> sqlParams) {
		SqlSession session = this.getSqlSession();
		return session.selectOne("article.totalArticlesByConditions", sqlParams);
	}

	@Override
	public Article findArticleById(long articleId) {
		return getSqlSession().selectOne("article.findArticleById", articleId);
	}

	@Override
	public long save(Article article) {
		article.setCreateAt(System.currentTimeMillis());
		article.setUpdateAt(System.currentTimeMillis());
		SqlSession session = this.getSqlSession();
		int rows = session.insert("article.save", article);
		return rows > 0 ? article.getId() : -1;
	}

	@Override
	public int update(Article article) {
		article.setUpdateAt(System.currentTimeMillis());
		return getSqlSession().update("article.update", article);
	}

}
