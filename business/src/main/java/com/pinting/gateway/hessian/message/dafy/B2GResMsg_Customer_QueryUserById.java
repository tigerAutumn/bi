package com.pinting.gateway.hessian.message.dafy;

import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_QueryUserById extends ResMsg {

	private static final long serialVersionUID = -1482842766500682207L;

	private Integer code;
	
	private String msg;
	
	private Map<String, Object> strSalesman;

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

	public Map<String, Object> getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(Map<String, Object> strSalesman) {
		this.strSalesman = strSalesman;
	}
}
