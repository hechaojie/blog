package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.AboutUS;

public interface AboutUSDao {
	
	public List<AboutUS> findAllByCondition(@Param("sqlParams")Map<String,Object> sqlParams);
	
}
