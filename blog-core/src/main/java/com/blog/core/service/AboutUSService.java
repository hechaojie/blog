package com.blog.core.service;

import java.util.List;
import java.util.Map;

import com.blog.core.entity.AboutUS;

/**
 * 描述：关于我业务处理接口
 * @author: hecj
 */
public interface AboutUSService {
	
	/**
	 * 描述：查询所有
	 * @author: hecj
	 */
	public List<AboutUS> findAllByCondition(Map<String,Object> params);
	
}
