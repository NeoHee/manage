package com.manage.modular.service;

import javax.servlet.http.HttpServletRequest;

import com.manage.modular.pojo.Page;
import com.manage.modular.pojo.QueryCriteria;
import com.manage.modular.pojo.Student;

public interface StudentService {
	/**
	 * 查询是否有重复的编码
	 * @param code
	 * @return
	 */
	String queryCode(Integer code);
	/**
	 * 保存学生
	 * @param student
	 * @param req 
	 * @return
	 */
	boolean save(Student student, HttpServletRequest req);
	/**
	 * 进入学生列表页面
	 * @param req 
	 * @param
	 * @return
	 */
	Page<Student> find(Page<Student> page, QueryCriteria qc, HttpServletRequest req);
	/**
	 * 删除学生
	 * @param code
	 * @return
	 */
	boolean del(Integer code);
	/**
	 * 得到一个学生详情
	 * @param code
	 * @return
	 */
	Student getId(Integer code);
	/**
	 * 更改信息状态 封存－－－－启封
	 * @param code
	 * @param stateId
	 * @return
	 */
	String chageState(Integer code, Integer stateId);
	/**
	 * 更新学生信息详情
	 * @return
	 */
	boolean updateStudent(Student st);
}
