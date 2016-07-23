package com.blog.service.dao.impl;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.service.dao.EmailAuthTokenDao;
import com.blog.core.entity.EmailAuthToken;

public class EmailAuthTokenDaoImpl extends SqlSessionDaoSupport implements EmailAuthTokenDao {

	@Override
	public long save(EmailAuthToken emailAuthToken) {
		emailAuthToken.setCreateAt(System.currentTimeMillis());
		return this.getSqlSession().insert("emailAuthToken.save",emailAuthToken);
	}

	@Override
	public EmailAuthToken findByToken(String token) {
		return this.getSqlSession().selectOne("emailAuthToken.findByToken",token);
	}

	@Override
	public boolean update(EmailAuthToken emailAuthToken) {
		int rows = this.getSqlSession().update("emailAuthToken.update", emailAuthToken);
		return rows>0?true:false;
	}

}
