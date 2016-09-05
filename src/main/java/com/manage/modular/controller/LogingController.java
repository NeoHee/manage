package com.manage.modular.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.manage.modular.pojo.User;
import com.manage.modular.service.LoginService;
//@Component
//@RequestMapping("/common")
//@SpringBootApplication
@Controller()
@RequestMapping("manage/common")
public class LogingController {
	
	private LoginService service;
	
	public LoginService getService() {
		return service;
	}
	@Autowired()
	public void setService(LoginService service) {
		this.service = service;
	}

	/**
	 * 跳转到登陆页面
	 * @param mode
	 * @return
	 */
	@RequestMapping("login.html")
	public ModelAndView showLogPage(Model mode) {
		ModelAndView md = new ModelAndView("login");
		return md;
	}
	
	/**
	 * 注册
	 */
	@RequestMapping("reg.do")
	@ResponseBody
	public String reging(User user,HttpServletRequest req) {
		boolean rs = service.saveUser(user,req);
		return String.valueOf(rs);
	}
	
	/**
	 * 登陆
	 */
	@RequestMapping("log.do")
	@ResponseBody
	public String loging(User user,HttpServletRequest req) {
		boolean rs = service.login(user,req);
		return String.valueOf(rs);
	}
	/**
	 * 退出登陆
	 * @return
	 */
	@RequestMapping("logout.do")
	public ModelAndView logOut(HttpServletRequest req) {
		//注销当前Session
		req.getSession().setAttribute("user",null);
		return new ModelAndView("login");
	}
	
	@RequestMapping("index.do")
	public ModelAndView headHtml(User user,HttpServletRequest req) {
		//获取Session  如果session为空说明没有用户登陆过，不为空就说明有用户登陆，session超时设置为60
		Object obj = req.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		if(obj != null){
			model.addObject("user",user);
			model.setViewName("index");
			//跳转首页
			return model;
		}else{
			//跳转登陆页面
			model.setViewName("login");
			return model;
		}
	}
	
	@RequestMapping("rightPage.do")
	public ModelAndView first(){
		return new ModelAndView("welcome");
	}
	
}
