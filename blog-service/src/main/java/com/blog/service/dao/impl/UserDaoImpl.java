package com.blog.service.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.service.dao.UserDao;
import com.blog.core.entity.User;

public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
	
	@Resource
	private UserDao userDao;
	
	@Override
	public List<User> findAll() {
		return this.getSqlSession().selectList("findAll");
	}

	@Override
	public List<User> findUsersByConditions(Map<String, Object> params,
			long currPage, int pageSize) {
		
		params.put("start", pageSize * (currPage-1));
		params.put("offset", pageSize);
		
		SqlSession session = this.getSqlSession();
		List<User> users = session.selectList("user.findUsersByConditions", params);
		
		return users;
	}

	@Override
	public long totalUsersByConditions(Map<String, Object> params) {
		return getSqlSession().selectOne("user.totalUsersByConditions", params);
	}

	@Override
	public User findUserById(long userId) {
		return getSqlSession().selectOne("user.findUserById", userId);
	}

	@Override
	public User findUserByEmail(String email) {
		return getSqlSession().selectOne("user.findUserByEmail", email);
	}

	@Override
	public boolean updateUserPasswd(long userId, String password) {
		Map<String,Object> sqlParams = new HashMap<String,Object>();
		sqlParams.put("userId", userId);
		sqlParams.put("password", password);
		this.getSqlSession().update("user.updateUserPasswd", sqlParams);
		return false;
	}

	@Override
	public long save(User user) {
		user.setCreateAt(System.currentTimeMillis());
		user.setUpdateAt(System.currentTimeMillis());
		int rows = this.getSqlSession().insert("user.save",user);
		return rows>0?user.getId():-1l;
	}

}
