package com.blog.core.service;

import java.util.List;
import java.util.Map;

import com.blog.core.entity.AboutUS;

/**
 * @功能描述 关于我业务处理接口
 * @Version		V1.0
 * @Date		2016-1-6 下午4:05:08
 * @author hechaojie
 */
public interface AboutUSService {
	
	/**
	 * @功能描述 查询所有
	 * @return List<AboutUS>
	 * @Version		V1.0
	 * @date		2016-1-6 下午4:05:57
	 * @author hechaojie
	 * @modify
	 */
	public List<AboutUS> findAllByCondition(Map<String,Object> params);
	
}
