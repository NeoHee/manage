package com.manage.modular.pojo;
import java.io.Serializable;

public class QueryCriteria implements Serializable{
	private static final long serialVersionUID = -2386705131402752732L;
//	条件
	private String option;
//	从多久开始
	private String dateFrom;
//	到多久结束
	private String dateTo;
//	关键字
	private String keyWord;
//	类型
	private String typeId;
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
}
