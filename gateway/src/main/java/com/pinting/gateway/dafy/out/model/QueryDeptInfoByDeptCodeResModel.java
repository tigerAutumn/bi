package com.pinting.gateway.dafy.out.model;

import java.util.List;

public class QueryDeptInfoByDeptCodeResModel {

	private Integer code;
	
	private String msg;
	
	private List<DafyDeptInfoModel> strDepartment;

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

	public List<DafyDeptInfoModel> getStrDepartment() {
		return strDepartment;
	}

	public void setStrDepartment(List<DafyDeptInfoModel> strDepartment) {
		this.strDepartment = strDepartment;
	}
}