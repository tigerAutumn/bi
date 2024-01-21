package com.pinting.gateway.hessian.message.dafy;

import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.model.DafyUserInfoModel;

public class B2GResMsg_Customer_QueryUserByPhone extends ResMsg {

	private static final long serialVersionUID = 2099650593190308310L;

	private Integer code;
	
	private String msg;
	
	private Map<String, Object> strSalesman;

	public Map<String, Object> getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(Map<String, Object> strSalesman) {
		this.strSalesman = strSalesman;
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
