package com.blog.front.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blog.front.constant.ConfigProvider;
import com.blog.front.util.UserUtil;
import com.blog.core.entity.User;

public class ConstantInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		
//		request.setAttribute("STATIC_URL", ConfigProvider.STATIC_URL);
		request.setAttribute("RESOURCE_URL", ConfigProvider.RESOURCE_URL);
		User user = UserUtil.getUser(request.getSession());
		if(user != null){
			request.getSession().setAttribute("user", user);
		}
		return super.preHandle(request, response, handler);
	}
}
