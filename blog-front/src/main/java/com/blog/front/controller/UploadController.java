package com.blog.front.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.blog.core.service.UserService;
import com.blog.front.constant.ConfigProvider;
import com.hecj.common.utils.DateFormatUtil;
import com.hecj.common.utils.FileUtils;
import com.hecj.common.utils.IdWorker;
import com.hecj.common.utils.ObjectToJson;

@Controller
public class UploadController extends BaseController{
	
	@Resource
	public UserService userService; 
	
	/**
	 * 上传图片
	 * @return
	 */
	@RequestMapping(value="/p/upload/uploadFile")
	public void uploadFile(@RequestParam("uploadFile") MultipartFile file,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String ext = FileUtils.getExt(file.getOriginalFilename());
			String newFileName = DateFormatUtil.getCurrTimeStr()+new IdWorker(2).nextId()+"."+ext;
			
			file.transferTo(new File(ConfigProvider.uploadFileTempsDir+"/"+newFileName));
			
			String url = ConfigProvider.RESOURCE_URL + ConfigProvider.static_upload_file_temp_url +"/"+ newFileName;
			System.out.println("上传文件路径：" + url);
			
    		result.put("code", 200);
    		result.put("url", url);
    		write(response,ObjectToJson.object2json(result));
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", -100000l);
			write(response,ObjectToJson.object2json(result));
		}
	}
	
	/**
	 * 上传文件测试
	 * @return
	 */
	@RequestMapping(value="/p/upload/upload")
	@ResponseBody
	public String upload(@RequestParam(value = "file", required = false) CommonsMultipartFile file,
			HttpServletRequest request, HttpServletResponse response, ModelMap model){
		
		//解决跨域问题
		response.setHeader("Access-Control-Allow-Origin","*");
				
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String ext = FileUtils.getExt(file.getOriginalFilename());
			String newFileName = "test"+DateFormatUtil.getCurrTimeStr()+new IdWorker(2).nextId()+"."+ext;
			
			file.transferTo(new File(ConfigProvider.uploadFileTempsDir+"/"+newFileName));
			
			String url = ConfigProvider.RESOURCE_URL + ConfigProvider.static_upload_file_temp_url +"/"+ newFileName;
			System.out.println("上传文件路径：" + url);
			
    		result.put("code", 200);
    		result.put("url", url);
			return ObjectToJson.object2json(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", -100000l);
			return ObjectToJson.object2json(result);
		}
	}
	
	
	
}
