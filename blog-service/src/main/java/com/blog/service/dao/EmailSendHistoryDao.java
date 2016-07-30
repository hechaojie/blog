package com.blog.service.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.blog.core.entity.EmailSendHistory;
public interface EmailSendHistoryDao {
	
	/**
	 * 描述：查询发送邮件记录
	 * @author: hecj
	 */
	public List<EmailSendHistory> findByCondition(@Param("sqlParams")Map<String,Object> sqlParams,@Param("start")long start,@Param("size")int size);
	
	/**
	 * 描述：保存发送邮件记录
	 * @author: hecj
	 */
	public long save(EmailSendHistory esh); 
	
}
