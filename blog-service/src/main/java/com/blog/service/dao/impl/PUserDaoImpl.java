package com.blog.service.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.service.dao.PUserDao;
import com.blog.core.entity.PUser;

public class PUserDaoImpl extends SqlSessionDaoSupport implements PUserDao {
	
	@Override
	public List<PUser> findPUsersByConditions(Map<String, Object> params,
			long currPage, int pageSize) {
		
		params.put("start", pageSize * (currPage-1));
		params.put("offset", pageSize);
		
		SqlSession session = this.getSqlSession();
		List<PUser> users = session.selectList("pUser.findPUsersByConditions", params);
		
		return users;
	}

	@Override
	public long totalPUsersByConditions(Map<String, Object> params) {
		return 0;
	}

	@Override
	public PUser findPUserById(long puserId) {
		return getSqlSession().selectOne("pUser.findPUserById", puserId);
	}

	@Override
	public PUser findPUserByUsername(String username) {
		return getSqlSession().selectOne("pUser.findPUserByUsername", username);
	}

	@Override
	public boolean updatePUserPasswd(long puserId, String password) {
		Map<String,Object> sqlParams = new HashMap<String,Object>();
		sqlParams.put("puserId", puserId);
		sqlParams.put("password", password);
		this.getSqlSession().update("pUser.updatePUserPasswd", sqlParams);
		return false;
	}

	@Override
	public long save(PUser puser) {
		puser.setCreateAt(System.currentTimeMillis());
		puser.setUpdateAt(System.currentTimeMillis());
		int rows = this.getSqlSession().insert("pUser.save",puser);
		return rows>0?puser.getId():-1l;
	}

}
