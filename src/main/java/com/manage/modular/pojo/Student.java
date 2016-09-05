package com.manage.modular.pojo;

import java.io.Serializable;
import java.sql.Date;

public class Student implements Serializable {
	private static final long serialVersionUID = -8775897627624074532L;
	//编码
	private Integer code;
	//姓名
	private String name;
	//备注
	private String remark;
	//状态
	private Integer state;
	//创建日期
	private Date createDate;
	//最后登陆时间
	private Date endLogDate;
	//类型id
	private Integer typeId;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getEndLogDate() {
		return endLogDate;
	}
	public void setEndLogDate(Date endLogDate) {
		this.endLogDate = endLogDate;
	}
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	
}
