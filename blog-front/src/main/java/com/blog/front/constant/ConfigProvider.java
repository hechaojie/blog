package com.blog.front.constant;

/**
 * 常量配置类
 */
public class ConfigProvider {

	// public static String STATIC_URL = "";
	/**
	 * 运行环境 www,dev
	 */
	public static String env = "";
	
	public static String RESOURCE_URL = "";

	/**
	 * 上传文件临时目录
	 */
	public static String uploadFileTempsDir = "";

	/**
	 * 相对于URL路径
	 */
	public static String static_upload_file_temp_url = "";

	public static int publish_article_max_num = 20;
	
	public static int publish_article_comment_max_num = 100;

	public static int today_send_email_max_num = 20;
	/**
	 * 图片验证码key
	 */
	public static String SESSION_IMAGE_CODE = "SESSION_IMAGE_CODE";
	/**
	 * 图片验证码认证的Token
	 */
	public static String SESSION_IMAGE_CODE_TOKEN = "SESSION_IMAGE_CODE_TOKEN";

	public static String referer = "";

	private ConfigProvider() {

	}

	public void init() {

	}

	// public void setSTATIC_URL(String staticURL) {
	// STATIC_URL = staticURL;
	// }

	public void setRESOURCE_URL(String resource_URL) {
		RESOURCE_URL = resource_URL;
	}

	public static void setUploadFileTempsDir(String uploadFileTempsDir) {
		ConfigProvider.uploadFileTempsDir = uploadFileTempsDir;
	}

	public static void setStatic_upload_file_temp_url(String static_upload_file_temp_url) {
		ConfigProvider.static_upload_file_temp_url = static_upload_file_temp_url;
	}

	public static void setPublish_article_max_num(int publish_article_max_num) {
		ConfigProvider.publish_article_max_num = publish_article_max_num;
	}

	public static int getToday_send_email_max_num() {
		return today_send_email_max_num;
	}

	public static void setToday_send_email_max_num(int today_send_email_max_num) {
		ConfigProvider.today_send_email_max_num = today_send_email_max_num;
	}

	public static void setReferer(String referer) {
		ConfigProvider.referer = referer;
	}

	public static void setEnv(String env) {
		ConfigProvider.env = env;
	}

	public static void setPublish_article_comment_max_num(int publish_article_comment_max_num) {
		ConfigProvider.publish_article_comment_max_num = publish_article_comment_max_num;
	}

}
