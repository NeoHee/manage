package com.manage.modular.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manage.modular.dao.StudentDao;
import com.manage.modular.pojo.Page;
import com.manage.modular.pojo.QueryCriteria;
import com.manage.modular.pojo.Student;
import com.manage.modular.service.StudentService;
@Service("studentService")
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao dao;
	/**
	 * 查询是否有重复的编码
	 * @param code
	 * @return
	 */
	public String queryCode(Integer code) {
		return dao.queryCode(code);
	}
	/**
	 * 保存学生
	 * @param student
	 * @return
	 */
	public boolean save(Student student,HttpServletRequest req) {
		return dao.save(student,req);
	}
	/**
	 * 进入学生列表页面
	 * @param
	 * @return
	 */
	public Page<Student> find(Page<Student> page, QueryCriteria qc,HttpServletRequest req) {
		return dao.find(page,qc,req);
	}
	/**
	 * 删除学生
	 * @param code
	 * @return
	 */
	public boolean del(Integer code) {
		return dao.del(code);
	}
	/**
	 * 得到一个学生详情
	 * @param code
	 * @return
	 */
	public Student getId(Integer code) {
		// TODO Auto-generated method stub
		return dao.getId(code);
	}
	/**
	 * 更改信息状态 封存－－－－启封
	 * @param code
	 * @param stateId
	 * @return
	 */
	public String chageState(Integer code, Integer stateId) {
		// TODO Auto-generated method stub
		return dao.chageState(code,stateId);
	}
	/**
	 * 更新学生信息详情
	 * @return
	 */
	public boolean updateStudent(Student st) {
		// TODO Auto-generated method stub
		return dao.updateStudent(st);
	}

}
