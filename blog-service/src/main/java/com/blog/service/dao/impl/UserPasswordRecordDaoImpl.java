package com.blog.service.dao.impl;

import java.util.List;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.service.dao.UserPasswordRecordDao;
import com.blog.core.entity.UserPasswordRecord;

public class UserPasswordRecordDaoImpl extends SqlSessionDaoSupport implements UserPasswordRecordDao {

	@Override
	public List<UserPasswordRecord> findUserPasswordRecordsByUserId(
			long userId, long currPage, int pageSize) {
		return null;
	}

	@Override
	public boolean insert(UserPasswordRecord userPasswordRecord) {

		return false;
	}

}
