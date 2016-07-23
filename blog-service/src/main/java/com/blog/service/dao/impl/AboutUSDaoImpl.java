package com.blog.service.dao.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import com.blog.service.dao.AboutUSDao;
import com.blog.core.entity.AboutUS;

public class AboutUSDaoImpl extends SqlSessionDaoSupport implements AboutUSDao {
	
	@Override
	public List<AboutUS> findAllByCondition(Map<String, Object> sqlParams) {
		
		return this.getSqlSession().selectList("aboutUS.findAllByCondition", sqlParams);
	}
	
}
