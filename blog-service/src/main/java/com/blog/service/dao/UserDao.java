package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.User;

public interface UserDao {
	
	public List<User> findAll();
	
	public List<User> findUsersByConditions(@Param("sqlParams")Map<String,Object> sqlParams,@Param("start")long start,@Param("size")int size);
	
	public long totalUsersByConditions(Map<String,Object> params);

	public User findUserById(long id);
	
	public User findUserByEmail(String email);
	
	public boolean updateUserPasswd(@Param("userId")long userId,@Param("password")String password);
	
	public long save(User user);
}
