package com.blog.front.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.core.service.UserService;
import com.blog.front.constant.ConfigProvider;
import com.hecj.common.util.IdWorker;
import com.hecj.common.util.date.DateFormatUtil;
import com.hecj.common.util.file.FileUtil;
import com.hecj.common.util.result.ObjectToJson;

@Controller
@RequestMapping(value="/upload")
public class UploadController extends BaseController{
	
	@Resource
	public UserService userService; 
	
	/**
	 * 上传图片
	 * @return
	 */
	@RequestMapping(value="uploadFile")
	public void uploadFile(@RequestParam("uploadFile") MultipartFile file,HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String ext = FileUtil.getExt(file.getOriginalFilename());
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
	
	
}
