package com.pinting.gateway.hessian.message.dafy;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_QueryDeptUserInfo extends ResMsg {

	private static final long serialVersionUID = 6292582053930440270L;

	private Integer code;
	
	private String msg;
	
	private ArrayList<HashMap<String, Object>> strSalesman;

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

	public ArrayList<HashMap<String, Object>> getStrSalesman() {
		return strSalesman;
	}

	public void setStrSalesman(ArrayList<HashMap<String, Object>> strSalesman) {
		this.strSalesman = strSalesman;
	}
}
