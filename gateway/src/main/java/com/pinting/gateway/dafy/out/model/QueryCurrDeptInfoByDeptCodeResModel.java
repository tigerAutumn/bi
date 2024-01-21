package com.pinting.gateway.dafy.out.model;

public class QueryCurrDeptInfoByDeptCodeResModel {

	private Integer code;
	
	private String msg;
	
	private DafyDeptInfoModel strDepartInfo;

	public DafyDeptInfoModel getStrDepartInfo() {
		return strDepartInfo;
	}

	public void setStrDepartInfo(DafyDeptInfoModel strDepartInfo) {
		this.strDepartInfo = strDepartInfo;
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