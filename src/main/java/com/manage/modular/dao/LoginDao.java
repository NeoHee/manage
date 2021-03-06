package com.manage.modular.dao;

import javax.servlet.http.HttpServletRequest;

import com.manage.modular.pojo.User;

public interface LoginDao {
	/**
	 * 注册用户
	 * @param user
	 * @return
	 */
	boolean saveUser(User user,HttpServletRequest req);
	/**
	 * 登陆用户
	 * @param user
	 * @param req 
	 * @return
	 */
	boolean login(User user, HttpServletRequest req);
}
