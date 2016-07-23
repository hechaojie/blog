package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import com.blog.core.entity.AboutUS;

public interface AboutUSDao {
	
	public List<AboutUS> findAllByCondition(Map<String,Object> sqlParams);
	
}
