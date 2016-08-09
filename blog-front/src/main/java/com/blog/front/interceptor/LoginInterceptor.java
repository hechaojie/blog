package com.blog.front.interceptor;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blog.front.util.MacherUtil;
import com.blog.front.util.UserUtil;
import com.blog.service.core.entity.User;
import com.hecj.common.util.StringUtil;
/**
 * 描述：登录拦截器
 * @author: hecj
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Log log = LogFactory.getLog(LoginInterceptor.class);
	
	private static final String LOGIN_REG = "login.reg";
	
	private static List<String> loginRegPool = new ArrayList<String>();
	
	@Resource
	public UserUtil userUtil;
	
	static{
		try {
			loginRegPool = FileUtils.readLines(new File(LoginInterceptor.class.getClassLoader().getResource(LOGIN_REG).getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 例如 :
	 * http://hechaojie.com/article?page=2
	 * uri -> /article
	 * queryString -> page=2
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		String queryString = request.getQueryString();
		if(queryString == null){
			queryString = "";
		} else{
			queryString = "?"+queryString;
		}
		
		String fullUrl = request.getRequestURI() + queryString;
		
		if(!isLoginIn(fullUrl)){
			return super.preHandle(request, response, handler);
		}
		
		User user = userUtil.getUser(request.getSession());
		if(user == null){
			log.info("未登录，跳转到登录页面。"+fullUrl);
			//AJAX请求
			if(request.getHeader("x-requested-with") !=null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				log.info("ajax访问超时，跳转到登录页面。"+fullUrl);
				//给个状态码
				response.setStatus(999);
				return false;
			}else{
				response.sendRedirect("/login?bk="+URLEncoder.encode(fullUrl,"UTF-8"));
				return false;
			}
		} else{
			return super.preHandle(request, response, handler);
		}
	}
	
	
	/**
	 * 描述：正则判断是否需要登录拦截
	 * @author: hecj
	 */
	public static boolean isLoginIn(String url){
		for (String loginReg : loginRegPool) {
			if(!StringUtil.isStrEmpty(MacherUtil.matcher(loginReg, url))){
				return true;
			}
		}
		return false;
	}
	
}
