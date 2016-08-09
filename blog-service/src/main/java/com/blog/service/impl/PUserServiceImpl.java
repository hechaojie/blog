package com.blog.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.blog.service.PUserService;
import com.blog.service.core.entity.PUser;
import com.blog.service.dao.PUserDao;
import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;
@Service("pUserService")
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
	public boolean updatePassword(String id, String password) {
		return false;
	}

	@Override
	public PUser findPUserById(String id) {
		return null;
	}

}
