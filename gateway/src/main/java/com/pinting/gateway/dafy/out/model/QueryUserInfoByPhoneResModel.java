package com.pinting.gateway.dafy.out.model;

public class QueryUserInfoByPhoneResModel {

	private Integer code;
	
	private String msg;
	
	private DafyUserInfoModel strSalesman;

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

	public DafyUserInfoModel getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(DafyUserInfoModel strSalesman) {
		this.strSalesman = strSalesman;
	}
}
