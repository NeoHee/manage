package com.manage.modular.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.manage.modular.dao.LoginDao;
import com.manage.modular.pojo.User;

@Repository("loginDao")
public class LoginDaoImpl implements LoginDao{
	@Autowired
	private JdbcTemplate jdbc;
	/**
	 * 注册用户
	 */
	public boolean saveUser(final User user,HttpServletRequest req) {
		//查询是否有这个用户名
		String sql = "select * from tb_user where userName=?";
		List<Map<String, Object>> list = jdbc.queryForList(sql,new Object[]{user.getUserName()});
		int i = list.size();
		//i==0的时候说明没有重复的用户名s
		if(i==0){
			KeyHolder keyHolder = new GeneratedKeyHolder();
			final String sql1 = "insert into tb_user(userName,passWord) values(?,?)";
			int rs = jdbc.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement state = con.prepareStatement(sql1,PreparedStatement.RETURN_GENERATED_KEYS);
					state.setString(1,user.getUserName());
					state.setString(2, user.getPassWord());
					return state;
				}
			},keyHolder);
			//返回主键ID
			int id = keyHolder.getKey().intValue();
			//设置登陆的身份
			user.setId(id);
			req.getSession().setAttribute("user",user);
			return rs==0?false:true;
		}else{
			return false;
		}
	}
	/**
	 * 登陆用户
	 * @param user
	 * @return
	 */
	public boolean login(User user,HttpServletRequest req) {
		//查询是否有对应的用户
		List<Map<String, Object>> list = jdbc.queryForList("select * from tb_user where userName=? and passWord=?",new Object[]{user.getUserName(),user.getPassWord()});
		int i = list.size();
		//说有对应用户，并在Session中把用户储存为当前用户
		if (i==1) {
			int id = Integer.parseInt(list.get(0).get("id").toString());
			//设置登陆的身份
			user.setId(id);
			req.getSession().setAttribute("user",user);
		}
		return i==0?false:true;
	}
	
	

}
