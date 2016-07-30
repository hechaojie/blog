package com.blog.core.service;

import java.util.List;
import java.util.Map;

import com.blog.core.email.EmailVars;
import com.blog.core.entity.EmailAuthToken;
import com.hecj.common.utils.Pagination;
import com.hecj.common.utils.Result;

/**
 * @功能描述 邮件业务处理接口
 * @Version V1.0
 * @Date 2016-1-6 下午4:05:08
 * @author hechaojie
 */
public interface EmailService {

	public long saveEmailAuthToken(EmailAuthToken emailAuthToken);

	public EmailAuthToken findByToken(String token);
	/**
	 * 一次发送一个邮件
	 */
	public void sendEmail(String title, String template, EmailVars email);
	/**
	 * 一次发送多个邮件
	 */
	public void sendEmail(String title, String template, List<EmailVars> emailList);
	
	public boolean updateEmailAuthToken(EmailAuthToken emailAuthToken);
	
	/**
	 * 描述：查询邮件发送记录
	 * @author: hecj
	 */
	public Result findEmailSendHistoryByCondition(Map<String, Object> params,
			Pagination pagination);

}
