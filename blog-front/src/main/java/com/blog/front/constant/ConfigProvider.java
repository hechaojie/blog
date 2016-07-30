package com.blog.front.constant;

/**
 * 常量配置类
 */
public class ConfigProvider {

//	public static String STATIC_URL = "";
	
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

	public static int today_send_email_max_num = 20;
	
	private ConfigProvider(){
		
	}
	
	public void init(){
		
	}

//	public void setSTATIC_URL(String staticURL) {
//		STATIC_URL = staticURL;
//	}

	public void setRESOURCE_URL(String resource_URL) {
		RESOURCE_URL = resource_URL;
	}

	public static void setUploadFileTempsDir(String uploadFileTempsDir) {
		ConfigProvider.uploadFileTempsDir = uploadFileTempsDir;
	}

	public static void setStatic_upload_file_temp_url(
			String static_upload_file_temp_url) {
		ConfigProvider.static_upload_file_temp_url = static_upload_file_temp_url;
	}
	
	public static void setPublish_article_max_num(
			int publish_article_max_num) {
		ConfigProvider.publish_article_max_num = publish_article_max_num;
	}

	public static int getToday_send_email_max_num() {
		return today_send_email_max_num;
	}

	public static void setToday_send_email_max_num(int today_send_email_max_num) {
		ConfigProvider.today_send_email_max_num = today_send_email_max_num;
	}
	
}
