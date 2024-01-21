package com.pinting.gateway.dafy.out.model;

import java.util.List;

public class QueryUserInfoByCurrDeptCodeResModel {

	private Integer code;
	
	private String msg;
	
	private List<DafyUserInfoModel> strSalesman;

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

	public List<DafyUserInfoModel> getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(List<DafyUserInfoModel> strSalesman) {
		this.strSalesman = strSalesman;
	}
}
