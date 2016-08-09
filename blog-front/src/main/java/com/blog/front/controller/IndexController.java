package com.blog.front.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.blog.front.constant.ConfigProvider;
import com.blog.front.util.CheckCode;
import com.blog.front.util.NumberUtil;
import com.blog.front.util.UserUtil;
import com.blog.service.AboutUSService;
import com.blog.service.ArticleService;
import com.blog.service.ArticleTypeService;
import com.blog.service.EmailService;
import com.blog.service.UserService;
import com.blog.service.core.email.EmailVars;
import com.blog.service.core.entity.AboutUS;
import com.blog.service.core.entity.EmailAuthToken;
import com.blog.service.core.entity.User;
import com.hecj.common.util.GenerateUtil;
import com.hecj.common.util.ObjectUtils;
import com.hecj.common.util.StringUtil;
import com.hecj.common.util.date.DateFormatUtil;
import com.hecj.common.util.encryp.MD5;
import com.hecj.common.util.image.ImageValidateCode;
import com.hecj.common.util.patten.PattenUtils;
import com.hecj.common.util.result.Pagination;
import com.hecj.common.util.result.Result;
import com.hecj.common.util.result.ResultJson;

@Controller
public class IndexController extends BaseController{

	private static final Log log = LogFactory.getLog(IndexController.class);
	
	@Resource
	public UserService userService;
	
	@Resource
	public ArticleService articleService;

	@Resource
	public ArticleTypeService articleTypeService;
	@Resource
	public AboutUSService aboutUSService;
	
	@Resource
	public EmailService emailService;
	
	@Resource
	public UserUtil userUtil;
	
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
		String t = DateFormatUtil.getCurrTimeStr();
		model.addAttribute("t", t);
		request.getSession().setAttribute(ConfigProvider.SESSION_IMAGE_CODE_TOKEN, t);
		return "index/login";
	}
	

	/**
	 * 登录
	 * @param email
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/dologin", method=RequestMethod.POST)
	public String doLogin(String email, String password,String code, String bk, HttpServletRequest request,HttpServletResponse response,ModelMap model){

		try {
			
			if(StringUtil.isStrEmpty(code)){
				setMessage(request, -1,"您输入的验证码");
				return "forward:/login";
			}
			
			if(!code.equalsIgnoreCase((String) request.getSession().getAttribute(ConfigProvider.SESSION_IMAGE_CODE))){
				setMessage(request, -1,"您输入的验证码不正确");
				return "forward:/login";
			}
			
			User user = userService.findUserByEmail(email);
			if(user == null){
				setMessage(request, -1,"您输入的邮箱无效或不存在");
				return "forward:/login";
			}
			
			if(!MD5.md5crypt(password).equals(user.getPassword())){
				setMessage(request, -1,"您输入的密码不正确");
				return "forward:/login";
			}
			
			// 将登陆信息存入cookie
			userUtil.setUser(user, request.getSession());
			
			if(!StringUtil.isStrEmpty(bk)){
				return "redirect:"+bk;
			}
			
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			setMessage(request, -1,"网络繁忙，请稍后再试");
			return "forward:/login";
		}
	}
	
	/**
	 * 注销
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response){
		userUtil.removeUser(request.getSession());
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
	
	/**
	 * 注册发送邮件
	 * 1.判断已注册
	 * 2.生成token
	 * 3.发送注册邮件
	 */
	@RequestMapping(value="/doRegister",method=RequestMethod.POST)
	public String doRegister(String email,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			
			if(StringUtil.isStrEmpty(email) || !PattenUtils.isEmail(email)){
				setMessage(request, -1,"请输入合法的邮箱");
				return "index/register";
			}
			
			// 1.判断已注册
			User user  = userService.findUserByEmail(email);
			if(user != null){
				log.info("邮箱已被注册email ："+email);
				setMessage(request, -1,"邮箱已被注册");
				return "index/register";
			}
			
			// 2.生成token
			String token = GenerateUtil.generateId(15);
			log.info("email, token:  "+email + ","+ token);
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
			
			setMessage(request,200,"发送注册邮件成功，快去邮箱（"+email+"）设置密码吧");
			return "common/_message";
			
		} catch (Exception e) {
			log.error("发送注册邮件异常email："+email);
			e.printStackTrace();
			setMessage(request,-100000,"网络超时，请稍后再试");
			return "common/_message";
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
			return "index/setpasswd";
			
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
	@RequestMapping(value="/auth/setpwd",method=RequestMethod.POST)
	public String doSetPasswd(String token, String passwd, String repasswd, HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		try {
			
			EmailAuthToken emailAuthToken = emailService.findByToken(token);
			if(emailAuthToken == null){
				setMessage(request,-1,"您的认证已经过期，或重复提交，请核对后重试");
				return "common/_message";
			}
			
			// 1.验证token状态
			if(emailAuthToken.getIsVerify() == 1){
				setMessage(request,-1,"您的认证已经过期，或重复提交，请核对后重试");
				return "common/_message";
			}
			
			// 2.验证token有效期
			Long validAt = emailAuthToken.getValidAt();
			if (System.currentTimeMillis() > validAt.longValue()) {
				setMessage(request,-1,"您的认证已经过期，或重复提交，请核对后重试");
				return "common/_message";
			}
			
			if(StringUtil.isObjectEmpty(passwd)){
				setMessage(request,-1,"您输入密码不能为空，请核对后重试");
				return "common/_message";
			}
			
			if(!passwd.equals(repasswd)){
				setMessage(request,-1,"您输入的两次密码不一致，请核对后重试");
				return "common/_message";
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
			log.info("用户注册成功token,email ："+token+","+emailAuthToken.getEmail());
			setMessage(request,200,"恭喜您，邮箱注册成功，快去登录吧");
			return "common/_message";
		} catch (Exception e) {
			log.error("设置密码异常token："+token);
			e.printStackTrace();
			setMessage(request,-100000,"网络超时，请稍后再试");
			return "common/_message";
		}
	}

	/**
	 * 找回密码 步骤一
	 */
	@RequestMapping(value="/forgetone")
	public String toForgetPasswordStep1(HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		return "index/forgetone";
	}
	
	/**
	 * 找回密码
	 */
	@RequestMapping(value="/doforgetpasswd",method=RequestMethod.POST)
	public String doForgetPasswd(String email,String passwd,String repasswd,String emailCode,HttpServletRequest request,HttpServletResponse response,ModelMap model){
		
		log.info("email,emailCode:"+email+","+emailCode);
		try {
			CheckCode checkCode = (CheckCode) request.getSession().getAttribute("emailCode");
			if(checkCode == null){
				setMessage(request, -1,"请获取验证码重新提交");
				return "index/forgetone";
			}
			log.info(checkCode.toString());
			if(!(checkCode.getCode().equals(emailCode)&&checkCode.getSendObj().equals(email))){
				setMessage(request, -1,"验证码错误，请核对后重试");
				return "index/forgetone";
			}
			if(checkCode.getInvalidTime() < System.currentTimeMillis()){
				setMessage(request, -1,"验证码已失效，请核对后重试");
				return "index/forgetone";
			}
			if(!passwd.equals(repasswd)){
				setMessage(request, -1,"密码不一致，请核对后重试");
				return "index/forgetone";
			}
			User user = userService.findUserByEmail(email);
			if(user == null){
				setMessage(request, -1,"您输入的用户不存在，请核对后重试");
				return "index/forgetone";
			}
			userService.updatePassword(user.getId(), MD5.md5crypt(passwd));
			request.getSession().removeAttribute("emailCode");
			setMessage(request,200,"您的密码找回成功，快去登录吧");
			return "common/_message";
		} catch (Exception e) {
			e.printStackTrace();
			setMessage(request, -1,"您输入的用户不存在，请核对后重试");
			return "index/forgetone";
		}
	}
	
	/**
	 * 邮件验证码
	 */
	@RequestMapping(value="/sendcode",method=RequestMethod.POST)
	@ResponseBody
	public ResultJson getEmailCode(String email, HttpServletRequest request,HttpServletResponse response,ModelMap model){
		// 随机生成4位验证码
		String emailCode = NumberUtil.getRandomNumber(6);
		log.info("生成的邮件验证码："+email+","+emailCode);
		try {
			// 60秒内不重复发送邮件
			CheckCode oldCheckCode = (CheckCode) request.getSession().getAttribute("emailCode");
			if(oldCheckCode != null){
				if(System.currentTimeMillis()-oldCheckCode.getSendTime() < 1*60*1000){
					log.info("验证码已发送，60秒内有效 email,code :"+ oldCheckCode.getSendObj()+","+oldCheckCode.getCode());
					return new ResultJson(200l,"验证码已发送，60秒内有效");
				}
			}
			
			User user = userService.findUserByEmail(email);
			if(user == null){
				log.info("您输入的用户不存在 email:"+email);
				return new ResultJson(-1l,"您输入的用户不存在，请核对后重试");
			}
			
			// 同一个邮箱每天最多发送20封
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("reciverEmail", email);
			param.put("startTime", DateFormatUtil.getDayBegin(new Date()).getTime());
			param.put("endTime", DateFormatUtil.getDayEnd(new Date()).getTime());
			Result result = emailService.findEmailSendHistoryByCondition(param, new Pagination(Integer.MAX_VALUE));
			if(result.getData().size()>=ConfigProvider.today_send_email_max_num){
				log.info("您当天发送邮件次数超限，明天再来吧 email:"+email);
				return new ResultJson(-1l,"您当天发送邮件次数超限，明天再来吧");
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
			return new ResultJson(-100000l,"网络超时，请稍后再试");
		}
	}
	
	/**
	 * 描述：图片验证码
	 * @author: hecj
	 * @throws Exception 
	 */
	@RequestMapping(value="/logincode",method=RequestMethod.GET)
	public void loginCode(String t, HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception{
		
		String referer=request.getHeader("referer");
		if(StringUtil.isStrEmpty(referer) || (!referer.contains("/login") && !referer.contains("/dologin"))){
			log.error("referer验证失败 "+referer);
			String code = ObjectUtils.getUUID().substring(0,4);
			ImageValidateCode.imageCode(code, request, response);
			return;
		}
		
		String session_t = (String) request.getSession().getAttribute(ConfigProvider.SESSION_IMAGE_CODE_TOKEN);
		if(StringUtil.isStrEmpty(session_t) || !session_t.equals(t)){
			log.error("验证失败：t,session_t "+t+","+session_t);
			String code = ObjectUtils.getUUID().substring(0,4);
			ImageValidateCode.imageCode(code, request, response);
			return;
		}
		
		String code = ObjectUtils.getUUID().substring(0,4);
		ImageValidateCode.imageCode(code, request, response);
		request.getSession().setAttribute(ConfigProvider.SESSION_IMAGE_CODE, code);
	}
	
}
