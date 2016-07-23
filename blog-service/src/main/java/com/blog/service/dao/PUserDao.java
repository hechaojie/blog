package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import com.blog.core.entity.PUser;

public interface PUserDao {
	
	public List<PUser> findPUsersByConditions(Map<String,Object> params,long currPage,int pageSize);
	
	public long totalPUsersByConditions(Map<String,Object> params);

	public PUser findPUserById(long userId);
	
	public PUser findPUserByUsername(String username);
	
	public boolean updatePUserPasswd(long userId,String password);
	
	public long save(PUser puser);
}
