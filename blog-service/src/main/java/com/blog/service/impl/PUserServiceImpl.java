package com.blog.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import com.blog.service.dao.PUserDao;
import com.blog.core.entity.PUser;
import com.blog.core.service.PUserService;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;

public class PUserServiceImpl implements PUserService {

	@Resource
	private PUserDao pUserDao;
	
	@Override
	public Result findPUsersByCondition(Map<String, Object> params,
			Pagination pagination) {
		return null;
	}

	@Override
	public PUser findPUserByUsername(String username) {
		return pUserDao.findPUserByUsername(username);
	}

	@Override
	public boolean addPUser(PUser puser) {
		return false;
	}

	@Override
	public boolean updatePassword(long id, String password) {
		return false;
	}

	@Override
	public PUser findPUserById(long puserId) {
		return null;
	}

}
