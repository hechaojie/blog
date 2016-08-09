package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.service.core.entity.PUser;

public interface PUserDao {
	
	public List<PUser> findPUsersByConditions(@Param("sqlParams")Map<String,Object> sqlParams,@Param("start")long start,@Param("size")int size);
	
	public long totalPUsersByConditions(Map<String,Object> params);

	public PUser findPUserById(String userId);
	
	public PUser findPUserByUsername(String username);
	
	public boolean updatePUserPasswd(@Param("userId")String userId,@Param("password")String password);
	
	public long save(PUser puser);
}
