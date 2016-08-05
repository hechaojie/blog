package com.blog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.blog.core.email.EmailVars;
import com.blog.core.entity.EmailAuthToken;
import com.blog.core.entity.EmailSendHistory;
import com.blog.core.service.EmailService;
import com.blog.service.dao.EmailAuthTokenDao;
import com.blog.service.dao.EmailSendHistoryDao;
import com.blog.service.util.Constant;
import com.hecj.common.util.GenerateUtil;
import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;
import com.hecj.common.util.result.ResultSupport;

public class EmailServiceImpl implements EmailService {

	private static final Log log = LogFactory.getLog(EmailServiceImpl.class);
	
	@Resource
	private EmailAuthTokenDao emailAuthTokenDao;

	@Resource
	private EmailSendHistoryDao emailSendHistoryDao;
	
	@Resource
	private Constant constant;
	
	@Override
	public long saveEmailAuthToken(EmailAuthToken emailAuthToken) {
		emailAuthToken.setId(GenerateUtil.generateId());
		return emailAuthTokenDao.save(emailAuthToken);
	}

	@Override
	public EmailAuthToken findByToken(String token) {
		return emailAuthTokenDao.findByToken(token);
	}
	
	@Override
	public void sendEmail(String title, String template, EmailVars email) {
		List<EmailVars> emailList = new ArrayList<EmailVars>();
		emailList.add(email);
		sendEmail(title, template, emailList);
	}
	
	@Override
	public void sendEmail(String title, String template, List<EmailVars> emailList) {
		
	    String vars = convert(emailList);
	    log.info("邮件参数："+vars);
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httpost = new HttpPost(constant.getSendcloudUrl());

	    List<NameValuePair> params = new ArrayList<NameValuePair>();
	    params.add(new BasicNameValuePair("api_user", constant.getSendcloudApiUser()));
	    params.add(new BasicNameValuePair("api_key", constant.getSendcloudApiKey()));
	    params.add(new BasicNameValuePair("substitution_vars", vars));
	    params.add(new BasicNameValuePair("template_invoke_name", template));
	    params.add(new BasicNameValuePair("from", constant.getSendcloudFromMail()));
	    params.add(new BasicNameValuePair("fromname", constant.getSendcloudFromname()));
	    params.add(new BasicNameValuePair("subject", title));
	    // 模板发送不用传
//	    params.add(new BasicNameValuePair("html", content));
	    params.add(new BasicNameValuePair("resp_email_id", "true"));
	    try {

	    	httpost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	    	HttpResponse response = httpclient.execute(httpost);

		    if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) { // 正常返回
		        log.info(EntityUtils.toString(response.getEntity()));
		    } else {
		        System.err.println("error");
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			httpost.releaseConnection();
			
			EmailSendHistory esh = new EmailSendHistory();
			esh.setContent(template);
			esh.setTitle(title);
			esh.setId(GenerateUtil.generateId());
			esh.setCreateAt(System.currentTimeMillis());
			esh.setReciverEmail(emailList.get(0).getEmail());
			esh.setIsDelete(0);
			esh.setType(1);
			emailSendHistoryDao.save(esh);
		}
	}
	
	/**
	 * 邮件参数转换
	 */
	private static String convert(List<EmailVars> emailList) {

	    JSONObject ret = new JSONObject();
	    // 参数keys
	    Set<String> keys = emailList.get(0).getVars().keySet();
	    // keys data
	    Map<String, List<Object>> keyDataMap = new HashMap<String, List<Object>>();
	    
	    for(String key : keys){
	    	keyDataMap.put(key, new ArrayList<Object>());
	    }
	    
	    JSONArray to = new JSONArray();
	    for (EmailVars ev : emailList) {
	    	 // 手机人邮件列表
	    	to.put(ev.getEmail());
	    	// 数据集
	    	Map<String,String> vars = ev.getVars();
	    	Set<String> tempKeys = ev.getVars().keySet();
	    	for(String tempKey : tempKeys){
	    		List<Object> datas = keyDataMap.get(tempKey);
	    		datas.add(vars.get(tempKey));
	    	}
	    }

	    ret.put("to", to);
	    ret.put("sub", keyDataMap);

	    return ret.toString();
	}

	@Override
	public boolean updateEmailAuthToken(EmailAuthToken emailAuthToken) {
		return emailAuthTokenDao.update(emailAuthToken);
	}

	@Override
	public Result findEmailSendHistoryByCondition(Map<String, Object> params, Pagination pagination) {
		Result result = new ResultSupport();
		try {
			List<EmailSendHistory> list = emailSendHistoryDao.findByCondition(params, pagination.getStartCursor(),pagination.getPageSize());
			result.setData(list);
			result.setPagination(pagination);
			result.setResult(true);
		} catch (Exception e) {
			log.error(" params {} "+ params);
			e.printStackTrace();
			result.setResult(false);
		}
		return result;
	}

}
