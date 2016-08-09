package com.blog.service.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.blog.service.core.entity.UserPasswordRecord;
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
	public List<UserPasswordRecord> findUserPasswordRecordsByUserId(@Param("userId")String userId,@Param("start")long start,@Param("size")int size);
	/**
	 * @param userPasswordRecord
	 * @return
	 */
	public boolean insert(UserPasswordRecord userPasswordRecord);
}
