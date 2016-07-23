package com.blog.front.controller;

import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.blog.core.email.EmailVars;
import com.blog.core.entity.EmailAuthToken;
import com.blog.core.entity.User;
import com.blog.core.service.EmailService;
import com.blog.core.service.UserService;
import com.blog.front.util.CheckCode;
import com.hecj.common.utils.ResultJson;
import com.hecj.common.utils.StringUtil;
import com.hecj.common.utils.encryp.MD5;

@Controller
public class UserController extends BaseController{
	
	private static final Log log = LogFactory.getLog(UserController.class);
	
	@Resource
	public UserService userService; 
	
	@Resource
	public EmailService emailService;
	
	
	/**
	 * 注册发送邮件
	 * 1.判断已注册
	 * 2.生成token
	 * 3.发送注册邮件
	 */
	@RequestMapping(value="/p/user/sendMailReg")
	@ResponseBody
	public ResultJson sendMailReg(String email,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			// 1.判断已注册
			User user  = userService.findUserByEmail(email);
			if(user != null){
				log.info("邮箱已被注册email ："+email);
				return new ResultJson(-1l, "邮箱已被注册");
			}
			
			// 2.生成token
			String token = UUID.randomUUID().toString();
			log.info("email {} , token {} :  "+email + ","+ token);
			//生成注册token
			EmailAuthToken emailAuthToken = new EmailAuthToken();
			emailAuthToken.setEmail(email);
			emailAuthToken.setToken(token);
			emailAuthToken.setType(2);
			emailAuthToken.setIsVerify(0);
			// 24小时有效
			emailAuthToken.setValidAt(System.currentTimeMillis()+24*60*60*1000);
			emailService.saveEmailAuthToken(emailAuthToken);
			
			// 3.发送注册邮件
			EmailVars emailVars = new EmailVars().setEmail(email)
					.putVar("%email%", email)
					.putVar("%token%", token);
			emailService.sendEmail("注册激活邮件", "email_user_reg_auth_token", emailVars);
			
			return new ResultJson(200l, "发送注册邮件成功");
		} catch (Exception e) {
			log.error("发送注册邮件异常email："+email);
			e.printStackTrace();
			return new ResultJson(-100000l, "发送邮件失败");
		}
	}
	
	/**
	 * 验证token,设置密码页面
	 * 1.验证token状态
	 * 2.验证token有效期
	 */
	@RequestMapping(value="/auth/set_passwd")
	public String toSetPasswd(String token,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			
			EmailAuthToken emailAuthToken = emailService.findByToken(token);
			if(emailAuthToken == null){
				return "forward:/404";
			}
			
			// 1.验证token状态
			if(emailAuthToken.getIsVerify() == 1){
				return "forward:/404";
			}
			
			// 2.验证token有效期
			Long validAt = emailAuthToken.getValidAt();
			if (System.currentTimeMillis() > validAt.longValue()) {
				return "forward:/404";
			}
			
			model.addAttribute("token", token);
			return "index/set_passwd";
			
		} catch (Exception e) {
			log.error("设置密码异常token："+token);
			e.printStackTrace();
			return "forward:/404";
		}
	}
	
	/**
	 * 验证token,设置密码页面
	 * 1.验证token状态
	 * 2.验证token有效期
	 * 3.生成用户
	 */
	@RequestMapping(value="/p/user/setpwd")
	@ResponseBody
	public ResultJson doSetPasswd(String token, String passwd, String repasswd, HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			
			EmailAuthToken emailAuthToken = emailService.findByToken(token);
			if(emailAuthToken == null){
				return new ResultJson(-1l,"token无效");
			}
			
			// 1.验证token状态
			if(emailAuthToken.getIsVerify() == 1){
				return new ResultJson(-1l,"token无效");
			}
			
			// 2.验证token有效期
			Long validAt = emailAuthToken.getValidAt();
			if (System.currentTimeMillis() > validAt.longValue()) {
				return new ResultJson(-1l,"token已过期");
			}
			
			if(StringUtil.isObjectEmpty(passwd)){
				return new ResultJson(-2l, "密码不能为空");
			}
			
			if(!passwd.equals(repasswd)){
				return new ResultJson(-3l, "密码不一致");
			}
			
			// 生成用户
			User user = new User();
			user.setEmail(emailAuthToken.getEmail());
			user.setNickname(emailAuthToken.getEmail());
			user.setPassword(MD5.md5crypt(passwd));
			user.setUsername(emailAuthToken.getEmail());
			user.setSex(-1);
			userService.addUser(user);
			
			emailAuthToken.setIsVerify(1);
			emailAuthToken.setVerifyAt(System.currentTimeMillis());
			emailService.updateEmailAuthToken(emailAuthToken);
			
			// 发送注册成功邮件
			EmailVars emailVars = new EmailVars();
			emailVars.setEmail(emailAuthToken.getEmail());
			emailVars.putVar("%email%", emailAuthToken.getEmail());
			emailService.sendEmail("注册成功通知", "email_user_reg_success", emailVars);
			log.info("用户注册成功token {},email {} ："+token+","+emailAuthToken.getEmail());
			return new ResultJson(200l, "密码设置成功");
		} catch (Exception e) {
			log.error("设置密码异常token："+token);
			e.printStackTrace();
			return new ResultJson(-10000l, "服务器异常");
		}
	}
	
	/**
	 * to找回密码 步骤一
	 */
	@RequestMapping(value="/p/to_forget_passwd_step1")
	public String toForgetPasswordStep1(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		return "index/forget_passwd_step1";
	}
	
	/**
	 * to找回密码 步骤二
	 */
	@RequestMapping(value="/p/to_forget_passwd_step2")
	public String toForgetPasswordStep2(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		return "index/forget_passwd_step2";
	}
	
	/**
	 * do找回密码
	 */
	@RequestMapping(value="/p/user/do_forget_passwd",method=RequestMethod.POST)
	@ResponseBody
	public ResultJson doForgetPasswd(String email,String passwd,String repasswd,String emailCode,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		log.info("email{},emailCode{}:"+email+","+emailCode);
		try {
			CheckCode checkCode = (CheckCode) request.getSession().getAttribute("emailCode");
			if(checkCode == null){
				return new ResultJson(-4l,"请获取验证码重新提交");
			}
			log.info(checkCode.toString());
			if(!(checkCode.getCode().equals(emailCode)&&checkCode.getSendObj().equals(email))){
				return new ResultJson(-1l,"验证码错误");
			}
			if(checkCode.getInvalidTime() < System.currentTimeMillis()){
				return new ResultJson(-2l,"验证码已失效");
			}
			if(!passwd.equals(repasswd)){
				return new ResultJson(-3l,"密码不一致");
			}
			User user = userService.findUserByEmail(email);
			if(user == null){
				return new ResultJson(-4l,"您输入的用户不存在");
			}
			userService.updatePassword(user.getId(), MD5.md5crypt(passwd));
			request.getSession().removeAttribute("emailCode");
			return new ResultJson(200l,"获取验证码成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ResultJson(-100000l,"请求数据出错"); 
		}
	}
	
	/**
	 * 找回密码-获取邮件验证码
	 */
	@RequestMapping(value="/p/user/get_email_code")
	@ResponseBody
	public ResultJson getEmailCode(String email, HttpServletRequest request,HttpServletResponse response,ModelMap model){
		// 随机生成4位验证码
		String emailCode = com.blog.front.util.NumberUtil.getRandomNumber(6);
		log.info("生成的邮件验证码："+email+","+emailCode);
		try {
			// 60秒内不重复发送邮件
			CheckCode oldCheckCode = (CheckCode) request.getSession().getAttribute("emailCode");
			if(oldCheckCode != null){
				if(System.currentTimeMillis()-oldCheckCode.getSendTime() < 1*60*1000){
					log.info("验证码已发送，60秒内有效 email {},code {} :"+ oldCheckCode.getSendObj()+","+oldCheckCode.getCode());
					return new ResultJson(200l,"验证码已发送，60秒内有效");
				}
			}
			
			User user = userService.findUserByEmail(email);
			if(user == null){
				log.info("您输入的用户不存在 email{}:"+email);
				return new ResultJson(-1l,"您输入的用户不存在");
			}
			
			// 1.发送邮件验证码
			EmailVars emailVars = new EmailVars();
			emailVars.setEmail(email);
			emailVars.putVar("%check_code%", String.valueOf(emailCode));
			emailService.sendEmail("邮件找回密码", "email_forget_passwd_checkcode", emailVars);
			
			CheckCode checkCode = new CheckCode();
			checkCode.setCode(String.valueOf(emailCode));
			checkCode.setSendObj(email);
			checkCode.setSendTime(System.currentTimeMillis());
			checkCode.setInvalidTime(System.currentTimeMillis()+10*60*1000);
			// 2.将验证码放入sessin
			request.getSession().setAttribute("emailCode", checkCode);
			return new ResultJson(200l,"获取验证码成功");
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return new ResultJson(-100000l,"发送邮件验证码失败");
		}
	}
	
}
