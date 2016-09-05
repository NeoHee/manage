package com.manage.modular.controller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manage.modular.pojo.Page;
import com.manage.modular.pojo.QueryCriteria;
import com.manage.modular.pojo.Student;
import com.manage.modular.service.StudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired()
	private StudentService service;
	/**
	 * 进入学生列表页面 查询全部
	 * @param
	 * @return
	 */
	@RequestMapping("list.do")
	public  ModelAndView list(HttpServletRequest req, HttpServletResponse resp) {
		Page<Student> page = service.find(new Page<Student>(req,resp),null,req);
		ModelAndView model = new ModelAndView();
		model.addObject("page",page);
		model.setViewName("student_list");
		return model;
	}
	/**
	 * 进入添加学生页面
	 * @param
	 * @return
	 */
	@RequestMapping("addPage.do")
	public  ModelAndView add() {
		return new ModelAndView("student_add");
	}
	/**
	 *添加学生
	 * @param student
	 * @return
	 */
	@RequestMapping("add.do")
	public ModelAndView save(Student student,HttpServletRequest req) {
		service.save(student,req);
		return new ModelAndView("redirect:/student/list.do");
	}
	
	/**
	 * 删除学生
	 * @param code
	 * @return
	 */
	@RequestMapping("del.do")
	public ModelAndView delStudent(Integer code) {
		service.del(code);
		return new ModelAndView("redirect:/student/list.do");
	}
	/**
	 * 跳转到修改页面
	 * @param code
	 * @return
	 */
	@RequestMapping("editPage.do")
	public ModelAndView refEditPage(Integer code) {
		Student st = service.getId(code);
		ModelAndView model = new ModelAndView();
		model.setViewName("student_edit");
		model.addObject("st",st);
		return model;
	}
	/**
	 * 查询是否有重复的编码
	 * @param code
	 * @return
	 */
	@RequestMapping("queryCode.do")
	@ResponseBody
	public String queryCode(Integer code) {
		return service.queryCode(code);
	}
	
	/**
	 * 更改信息状态 封存－－－－启封
	 * @param code
	 * @param stateId
	 * @return
	 */
	@RequestMapping("chageState.do")
	@ResponseBody
	public String chageState(Integer code,Integer stateId) {
		return  service.chageState(code,stateId);
	}

	/**
	 * 更新学生信息详情
	 * @return
	 */
	@RequestMapping("update.do")
	public ModelAndView updateInfo(Student st) {
		service.updateStudent(st);
		return new ModelAndView("redirect:/student/list.do");
	}
	
	/**
	 * @param qc 根据条件查询
	 * @param req
	 * @param resp
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("queryList.do")
	@ResponseBody
	public void queryList(QueryCriteria qc,HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Page<Student> page = service.find(new Page<Student>(req,resp),qc,req);
		String html = page.toString();
		html = html.replaceAll("turnOverPage","page");
		page.setPageHtml(html);
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(page);
		resp.setCharacterEncoding( "UTF-8");
		resp.setContentType("text/html;");
		resp.setHeader("Cache-Control", "no-cache");
		resp.getWriter().write(json);
		//return json;
	}
	
}
