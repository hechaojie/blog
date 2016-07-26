package com.blog.front.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.core.entity.AboutUS;
import com.blog.core.entity.User;
import com.blog.core.service.AboutUSService;
import com.blog.core.service.ArticleService;
import com.blog.core.service.ArticleTypeService;
import com.blog.core.service.UserService;
import com.blog.front.util.UserUtil;
import com.hecj.common.utils.StringUtil;
import com.hecj.common.utils.encryp.MD5;

@Controller
public class IndexController extends BaseController{

	@Resource
	public UserService userService;
	
	@Resource
	public ArticleService articleService;

	@Resource
	public ArticleTypeService articleTypeService;
	@Resource
	public AboutUSService aboutUSService;
	/**
	 * 网站入口
	 */
	@RequestMapping(value="/")
	public String index(HttpServletRequest request,HttpServletResponse response,ModelMap model){
//		redirect/forward:url
		return "forward:/article";
	}
	
	/**
	 * 登录
	 */
	@RequestMapping(value="/login")
	public String login(String bk,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		model.addAttribute("bk", bk);
		return "index/login";
	}
	

	/**
	 * 登录
	 * @param email
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/dologin", method=RequestMethod.POST)
	public String doLogin(String email, String password,String bk, HttpServletRequest request,HttpServletResponse response,ModelMap model){

		try {
			User user = userService.findUserByEmail(email);
			if(user == null){
				model.addAttribute("_error","您输入的邮箱无效或不存在");
				return "forward:/login";
			}
			
			if(!MD5.md5crypt(password).equals(user.getPassword())){
				model.addAttribute("_error","您输入的密码不正确");
				return "forward:/login";
			}
			
			// 将登陆信息存入cookie
			UserUtil.setUser(user, request.getSession());
			
			if(!StringUtil.isStrEmpty(bk)){
				return "redirect:"+bk;
			}
			
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("_error","网络繁忙，请稍后再试");
			return "forward:/login";
		}
	}
	
	/**
	 * 注销
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		UserUtil.removeUser(request.getSession());
		return "redirect:/";
	}
	
	/**
	 * 注册
	 */
	@RequestMapping(value="/register")
	public String register(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		return "index/register";
	}
	
	/**
	 * 关于我们 
	 */
	@RequestMapping(value="/us")
	public String us(HttpServletRequest request,HttpServletResponse response,ModelMap model){

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("idDelete", 0);
		List<AboutUS> aboutUSList = aboutUSService.findAllByCondition(params);
		model.addAttribute("aboutUSList",aboutUSList);
		return "system/aboutUs";
	}
	
	/**
	 * 404页面
	 */
	@RequestMapping(value="/404")
	public String _404(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		return "common/404";
	}
	
}
