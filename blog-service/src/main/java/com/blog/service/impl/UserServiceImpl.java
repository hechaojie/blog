package com.blog.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.blog.core.entity.User;
import com.blog.core.service.UserService;
import com.blog.service.dao.EmailAuthTokenDao;
import com.blog.service.dao.UserDao;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
import com.hecj.common.utils.ResultSupport;
import com.hecj.common.utils.StringUtil;

public class UserServiceImpl implements UserService {

	@Resource
	private UserDao userDao;
	
	@Resource
	private EmailAuthTokenDao emailAuthTokenDao;
	
	@Override
	public List<User> findAll() {
		return userDao.findAll();
	}

	@Override
	public Result findUsersByCondition(Map<String, Object> params, Pagination pagination) {
		Result result = new ResultSupport();
		try {
			
			Map<String, Object> sqlParams = new HashMap<String,Object>();
			if(!StringUtil.isObjectEmpty(params.get("email"))){
				sqlParams.put("email", params.get("email"));
			}
			
			List<User> userList = userDao.findUsersByConditions(sqlParams, pagination.getCurrPage(),pagination.getPageSize());
			long userTotal = userDao.totalUsersByConditions(sqlParams);
			
			pagination.setCountSize(userTotal);
			result.setData(userList);
			result.setPagination(pagination);
			result.setResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(false);
		}
		return result;
	}

	@Override
	public User findUserByEmail(String email) {
		return userDao.findUserByEmail(email);
	}

	@Override
	public boolean addUser(User user) {
		userDao.save(user);
		return true;
	}

	@Override
	public boolean updatePassword(long userId, String password) {
		return userDao.updateUserPasswd(userId, password);
	}

	@Override
	public User findUserById(long userId) {
		return userDao.findUserById(userId);
	}

}
