package com.blog.front.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blog.front.constant.ConfigProvider;
import com.blog.front.util.UserUtil;
import com.blog.service.core.entity.User;
import com.hecj.common.util.http.RequestContext;

public class ConstantInterceptor extends HandlerInterceptorAdapter {

	@Resource
	public UserUtil userUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
		try {
			
			RequestContext.begin(request.getServletContext(), request, response);//初始化线程变量值
			request.setAttribute("RESOURCE_URL", ConfigProvider.RESOURCE_URL);
			User user = userUtil.getUser(request.getSession());
			if(user != null){
				request.getSession().setAttribute("user", user);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.preHandle(request, response, handler);
	}
}
