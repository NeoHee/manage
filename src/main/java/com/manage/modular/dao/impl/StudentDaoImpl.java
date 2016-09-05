package com.manage.modular.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.manage.modular.dao.StudentDao;
import com.manage.modular.pojo.Page;
import com.manage.modular.pojo.QueryCriteria;
import com.manage.modular.pojo.Student;
import com.manage.modular.pojo.User;
@Repository("studentDao")
public class StudentDaoImpl implements StudentDao{
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * 查询是否有重复的编码
	 * @param code
	 * @return
	 */
	public String queryCode(Integer code) {
		List<Map<String, Object>> list = jdbc.queryForList("select * from tb_student where code=?",new Object[]{code});
		int i = list.size();
		return i==0?"false":"true";
	}
	/**
	 * 保存学生
	 * @param student
	 * @return
	 */
	public boolean save(Student t,HttpServletRequest req) {
		//得到当前的登陆者
		User user = (User) req.getSession().getAttribute("user");
		int i = jdbc.update("insert into tb_student(code,name,remark,state,createDate,typeId,userId) values(?,?,?,?,?,?,?)",new Object[]{t.getCode(),t.getName(),t.getRemark(),t.getState(),t.getCreateDate(),t.getTypeId(),user.getId()});
		return i==1?true:false;
	}
	/**
	 * 进入学生列表页面
	 * @param
	 * @return
	 */
	public Page<Student> find(Page<Student> page, QueryCriteria qc,HttpServletRequest req) {
		//得到当前的登陆者
		User user = (User) req.getSession().getAttribute("user");
		//当是条件搜索时
		if(qc != null){
				return findOptionStrudnet(page,qc,req);
		}
		String sql = "select * from tb_student where userId = "+user.getId()+" limit ?,?";
		String sql1 = "select count(code) as size from tb_student where userId = "+user.getId();
		//查询总数
		List<Map<String, Object>> lis = jdbc.queryForList(sql1);
		if(lis.size() == 0){
			page.setTotalRecords(0);
		}else{
			page.setTotalRecords(Integer.parseInt(lis.get(0).get("size").toString()));
		}
		//分页
		Object[] params = new Object[]{page.getOffset(),page.getLineSize()};
		//得到对应的所有学生信息
		List<Student> list = getStudentList(sql,params);
		page.setList(list);
		return page;
	}
	/**
	 * 条件搜索
	 * @param page
	 * @param qc
	 * @return
	 */
	private Page<Student> findOptionStrudnet(Page<Student> page, QueryCriteria qc,HttpServletRequest req) {
		//得到当前的登陆者
		User user = (User) req.getSession().getAttribute("user");
		//条件
		String option = qc.getOption();
		//关键字
		String key = qc.getKeyWord();
		//从多久开始
		String dateFrom = qc.getDateFrom();
		//到多久结束
		String dateTo = qc.getDateTo();
		//类型
		String typeId = qc.getTypeId();
		
		StringBuffer sbf = null;
		sbf = new StringBuffer();
		sbf.append("SELECT * FROM tb_student WHERE userId = "+user.getId());
		switch (option) {
			//编码
			case "1":
				if(!"".equals(key)&&key.trim().length()!=0){
					sbf.append(" AND code LIKE '%"+key+"%'");
				}
				break;
			//姓名
			case "2":
				if(!"".equals(key)&&key.trim().length()!=0){
					sbf.append(" AND name LIKE '%"+key+"%'");
				}
				break;
			//类型
			case "3":
				if(!"".equals(typeId)&&typeId.trim().length()!=0){
					sbf.append(" AND typeId = "+typeId);
				}
				break;
			case "4":
				if(dateFrom.trim().length()>0 && dateTo.trim().length()>0){
					sbf.append(" AND createDate >= '"+dateFrom+"' AND createDate <= '"+dateTo+"' ");
				}else if(dateFrom.trim().length()>0 && dateTo.trim().length()==0){
					sbf.append(" AND createDate >= '"+dateFrom+"' ");
				}else if(dateFrom.trim().length()==0 && dateTo.trim().length()>0){
					sbf.append(" AND createDate <= '"+dateTo+"' ");
				}
				break;
		}
		Integer count =  getCount(sbf);
		sbf.append(" LIMIT ?,?");
		//设置对应的总条数
		page.setTotalRecords(count);
		//分页
		Object[] params = new Object[]{page.getOffset(),page.getLineSize()};
		//得到对应的学生信息
		List<Student> list = getStudentList(sbf.toString(),params);
		page.setList(list);
		return page;
	}
	
	//得到学生数据对象
	private List<Student> getStudentList(String sql,Object[] params) {
		List<Student> list = jdbc.query(sql,new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Student st = new Student();
				//编码
				st.setCode(rs.getInt("code"));
				//姓名
				st.setName(rs.getString("name"));
				//类型
				st.setTypeId(rs.getInt("typeId"));
				//时间
				st.setCreateDate(rs.getDate("createDate"));
				//备注
				st.setRemark(rs.getString("remark"));
				//状态
				st.setState(rs.getInt("state"));
				return st;
			}
		},params);
		return list;
	}
	//得到对应条件的总数
	private Integer getCount(StringBuffer sbf) {
		String sql = sbf.toString();
		sql = sql.replace("SELECT * FROM tb_student", "SELECT COUNT(code) AS size FROM tb_student");
		//查询总数
		List<Map<String, Object>> lis = jdbc.queryForList(sql);
		return Integer.parseInt(lis.get(0).get("size").toString());
	}
	/**
	 * 删除学生
	 * @param code
	 * @return
	 */
	public boolean del(Integer code) {
		String sql = "delete from tb_student where code = ? ";
		int i = jdbc.update(sql,new Object[]{code});
		return i==1?true:false;
	}
	/**
	 * 得到一个学生详情
	 * @param code
	 * @return
	 */
	public Student getId(Integer code) {
		String sql = "select * from tb_student where code=?";
		List<Student> list = getStudentList(sql,new Object[]{code});
		return list.get(0);
	}
	/**
	 * 更改信息状态 封存－－－－启封
	 * @param code
	 * @param stateId
	 * @return
	 */
	public String chageState(Integer code, Integer stateId) {
		String sql = "update tb_student set state = ? where code = ?";
		int i = jdbc.update(sql,new Object[]{stateId,code});
		return i==0?"false":"true";
	}
	/**
	 * 更新学生信息详情
	 * @return
	 */
	@Transactional
	public boolean updateStudent(Student st) {
		String sql = "update tb_student set name=?,remark=?,state=?,createDate=?,typeId=? where code = ?";
		int i = jdbc.update(sql,new Object[]{st.getName(),st.getRemark(),st.getState(),st.getCreateDate(),st.getTypeId(),st.getCode()});
		return i==0?false:true;
	}
	
	
}
