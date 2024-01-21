package com.pinting.gateway.hessian.message.dafy;

import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_QueryCurrDeptInfo extends ResMsg {

	private static final long serialVersionUID = -8301445364798791033L;

	private Integer code;
	
	private String msg;
	
	private Map<String, Object> strDepartment;

	public Map<String, Object> getStrDepartment() {
		return strDepartment;
	}

	public void setStrDepartment(Map<String, Object> strDepartment) {
		this.strDepartment = strDepartment;
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
