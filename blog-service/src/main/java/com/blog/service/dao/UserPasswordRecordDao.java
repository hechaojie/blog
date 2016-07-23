package com.blog.service.dao;

import java.util.List;

import com.blog.core.entity.UserPasswordRecord;
/**
 * 用户历史密码
 */
public interface UserPasswordRecordDao {
	
	/**
	 * 查询用户历史密码
	 * @param userId
	 * @param currPage
	 * @param pageSize
	 * @return
	 */
	public List<UserPasswordRecord> findUserPasswordRecordsByUserId(long userId,long currPage,int pageSize);
	/**
	 * @param userPasswordRecord
	 * @return
	 */
	public boolean insert(UserPasswordRecord userPasswordRecord);
}
