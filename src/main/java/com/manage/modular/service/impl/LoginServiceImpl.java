package com.manage.modular.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.manage.modular.dao.LoginDao;
import com.manage.modular.pojo.User;
import com.manage.modular.service.LoginService;
@Service()
public class LoginServiceImpl implements LoginService {
	private LoginDao dao;
	
	@Autowired()
	public void setDao(LoginDao dao) {
		this.dao = dao;
	}
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	public boolean saveUser(User user,HttpServletRequest req) {
		return dao.saveUser(user,req);
	}
	/**
	 * 登陆用户
	 * @param user
	 * @return
	 */
	public boolean login(User user,HttpServletRequest req) {
		return dao.login(user,req);
	}
}
