package com.pinting.gateway.hessian.message.dafy;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_IncrementUserInfo extends ResMsg {

	private static final long serialVersionUID = 1177036255143631900L;

	private Integer code;
	
	private String msg;
	
	private Integer isNext;
	
	private ArrayList<HashMap<String, Object>> userList;

	public Integer getIsNext() {
		return isNext;
	}

	public void setIsNext(Integer isNext) {
		this.isNext = isNext;
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

	public ArrayList<HashMap<String, Object>> getUserList() {
		return userList;
	}

	public void setUserList(ArrayList<HashMap<String, Object>> userList) {
		this.userList = userList;
	}
}
