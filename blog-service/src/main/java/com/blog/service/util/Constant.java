package com.blog.service.util;

/**
 * @功能描述 常量配置类
 * @Version V1.0
 * @Date 2016-1-7 下午12:51:35
 * @author hechaojie
 */
public class Constant {

	/**
	 * sendcloud 邮件相关配置
	 */
	public static  String sendcloudUrl = "";
	public static  String sendcloudApiUser = "";
	public static  String sendcloudApiKey = "";
	public static  String sendcloudFromMail = "";
	public static  String sendcloudFromname = "";

	public static String solrServer = "";

	public String getSendcloudUrl() {
		return sendcloudUrl;
	}

	public void setSendcloudUrl(String sendcloudUrl) {
		this.sendcloudUrl = sendcloudUrl;
	}

	public String getSendcloudApiUser() {
		return sendcloudApiUser;
	}

	public void setSendcloudApiUser(String sendcloudApiUser) {
		this.sendcloudApiUser = sendcloudApiUser;
	}

	public String getSendcloudApiKey() {
		return sendcloudApiKey;
	}

	public void setSendcloudApiKey(String sendcloudApiKey) {
		this.sendcloudApiKey = sendcloudApiKey;
	}

	public String getSendcloudFromMail() {
		return sendcloudFromMail;
	}

	public void setSendcloudFromMail(String sendcloudFromMail) {
		this.sendcloudFromMail = sendcloudFromMail;
	}

	public String getSendcloudFromname() {
		return sendcloudFromname;
	}

	public void setSendcloudFromname(String sendcloudFromname) {
		this.sendcloudFromname = sendcloudFromname;
	}

	public String getSolrServer() {
		return solrServer;
	}

	public void setSolrServer(String solrServer) {
		this.solrServer = solrServer;
	}

}
