package com.pinting.gateway.dafy.out.model;

import java.util.List;

public class DafyIncrementDeptResModel {

	private Integer code;
	
	private String msg;
	
	private Integer isNext; //0-无下一页，1-有下一页
	
	private List<Tbdepartment> strDepartments;

	public Integer getIsNext() {
		return isNext;
	}

	public void setIsNext(Integer isNext) {
		this.isNext = isNext;
	}

	public List<Tbdepartment> getStrDepartments() {
		return strDepartments;
	}

	public void setStrDepartments(List<Tbdepartment> strDepartments) {
		this.strDepartments = strDepartments;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
