package com.blog.front.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.util.Base64;
import com.blog.service.UserService;
import com.blog.service.core.entity.User;
import com.hecj.common.util.ObjectUtils;
import com.hecj.common.util.http.RequestContext;

@Service
public class UserUtil {

    //SessionKey
    public final String USER_SESSION_KEY = "52c4d7655587401885409d97e97b1110";
    
    @Resource
    private UserService userService;
    
    public static void main(String[] args) {
		System.out.println(ObjectUtils.getUUID());
	}

    /**
     * 检测是否登录
     * @param httpSession 
     * @return
     */
    public boolean isLogin(HttpSession httpSession) {
        User user = getUser(httpSession);
        if (null == user) {
            return false;
        }
        return true;
    }

    /**
     * 从Session中获取用户
     *
     * @param httpSession
     * @return
     */
     public User getUser(HttpSession httpSession) {
    	 User user =  (User) httpSession.getAttribute(USER_SESSION_KEY);
    	 if(user == null){
    		 user =  getCookieUser(RequestContext.get().getRequest());
    		 if(user != null){
    			 setUser(user, RequestContext.get().getRequest().getSession());
    		 }
    	 }
    	 return user;
    }

    /**
     * 登录后设置User至session中.
     *
     * @param u
     * @param httpSession
     */
    public void setUser(User u, HttpSession httpSession) {
        httpSession.setAttribute(USER_SESSION_KEY, u);
        setCookie(RequestContext.get().getResponse(), u);
    }

    /**
     * 用户登出.
     * @param httpSession
     */
    public void removeUser(HttpSession httpSession) {
        httpSession.removeAttribute(USER_SESSION_KEY);
        httpSession.invalidate();
        clearCookie(RequestContext.get().getRequest(), RequestContext.get().getResponse(), "/");
    }
    
    /**
	 * 用户登录信息存入cookie
	 */
	public void setCookie(HttpServletResponse response, User user) {

		String sid = ObjectUtils.getUUID();
		// 会话ID&用户code
		String cookieString = sid + "&" + user.getId();
		cookieString = org.apache.commons.codec.binary.Base64.encodeBase64String(cookieString.getBytes());
		try {
			cookieString = URLEncoder.encode(cookieString, "utf-8");
		} catch (UnsupportedEncodingException e) {
		}
		Cookie cookie = new Cookie(USER_SESSION_KEY, cookieString);
		cookie.setPath("/");
		cookie.setMaxAge(31104000);// 这种将存在客户端当中...有效时间1年
		response.addCookie(cookie);
	}
	
	/**
	 * 清空cookie
	 */
	private void clearCookie(HttpServletRequest request, HttpServletResponse response, String path) {
		Cookie[] cookies = request.getCookies();
		try {
			for (int i = 0; i < cookies.length; i++) {
				// System.out.println(cookies[i].getName() + ":" +
				// cookies[i].getValue());
				Cookie cookie = new Cookie(cookies[i].getName(), null);
				cookie.setMaxAge(0);
				cookie.setPath(path);// 根据你创建cookie的路径进行填写
				response.addCookie(cookie);
			}
		} catch (Exception ex) {
			System.out.println("清空Cookies发生异常！");
		}

	} 
	
	/**
	 * 获取当前登陆用户
	 * @return
	 */
	public User getCookieUser(HttpServletRequest request) {
		
		
		Cookie cookies[] = request.getCookies();
		Cookie sCookie = null;
		String cookieStr = "";
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie.getName().equals(USER_SESSION_KEY)) {
					cookieStr = sCookie.getValue();
					if (ObjectUtils.isNotEmpty(cookieStr)){
						try {
							cookieStr = URLDecoder.decode(cookieStr, "utf-8");
							cookieStr =  new String(Base64.decodeFast(cookieStr));
						} catch (UnsupportedEncodingException e) {
						}
					}
				}
			}
		}
		if (ObjectUtils.isNotEmpty(cookieStr)){
			String str[] = cookieStr.split("&");
			return userService.findUserById(str[1]);
		}
		
		return null;
	}
	

}