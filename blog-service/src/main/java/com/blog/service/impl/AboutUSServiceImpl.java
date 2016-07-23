package com.blog.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.blog.service.dao.AboutUSDao;
import com.blog.core.entity.AboutUS;
import com.blog.core.service.AboutUSService;

public class AboutUSServiceImpl implements AboutUSService {

	@Resource
	private AboutUSDao aboutUSDao;
	
	@Override
	public List<AboutUS> findAllByCondition(Map<String, Object> params) {
		return aboutUSDao.findAllByCondition(params);
	}

}
