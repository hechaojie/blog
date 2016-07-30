package com.blog.core.service;

import java.util.List;
import java.util.Map;

import com.blog.core.entity.User;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;
/**
 * 用户接口类
 */
public interface UserService {
	
	/**
	 * 查询所有用户
	 * @return
	 */
	public List<User> findAll();
	
	/**
	 * @param params 条件
	 * @param pagination 分页参数
	 * @return
	 */
	public Result findUsersByCondition(Map<String,Object> params,Pagination pagination);
	
	/**
	 * 根据email查询用户
	 * @param email 邮箱
	 * @return
	 */
	public User findUserByEmail(String email);
	
	/**
	 * 添加用户
	 * @param user
	 * @return
	 */
	public boolean addUser(User user);

	/**
	 * 修改密码
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean updatePassword(String id, String password);
	
	/**
	 * 根据userId查询用户
	 */
	public User findUserById(String id);
	
}
