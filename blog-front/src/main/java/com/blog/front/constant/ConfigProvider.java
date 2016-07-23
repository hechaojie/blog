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
}
