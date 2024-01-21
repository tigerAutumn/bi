package com.pinting.gateway.hessian.message.dafy;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_QueryDeptInfo extends ResMsg {

	private static final long serialVersionUID = -6774327856063021248L;

	private Integer code;
	
	private String msg;
	
	private ArrayList<HashMap<String, Object>> strDepartment;

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

	public ArrayList<HashMap<String, Object>> getStrDepartment() {
		return strDepartment;
	}

	public void setStrDepartment(ArrayList<HashMap<String, Object>> strDepartment) {
		this.strDepartment = strDepartment;
	}
}
