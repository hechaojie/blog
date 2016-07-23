package com.blog.front.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blog.front.util.UserUtil;
import com.blog.core.entity.User;

public class UserInterceptor extends HandlerInterceptorAdapter {

	private static final Log log = LogFactory.getLog(UserInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		User user = UserUtil.getUser(request.getSession());
		String queryString = request.getQueryString();
		String uri = request.getRequestURI();
		String back_url = uri;
		if(queryString != null){
			back_url = uri+"?"+queryString;
		}
		if(user == null){
			log.info("未登录，跳转到登录页面");
			//AJAX请求
			if(request.getHeader("x-requested-with") !=null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				log.info("ajax访问超时，跳转到登录页面。");
				//给个状态码
				response.setStatus(999);
				return false;
			}else{
				response.sendRedirect("/login?b="+URLEncoder.encode(back_url,"UTF-8"));
				return false;
			}
		} else{
			return super.preHandle(request, response, handler);
		}
	}
}
