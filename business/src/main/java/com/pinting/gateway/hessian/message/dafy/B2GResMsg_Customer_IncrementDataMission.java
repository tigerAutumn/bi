package com.pinting.gateway.hessian.message.dafy;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_IncrementDataMission extends ResMsg {

	private static final long serialVersionUID = 8371788499119095193L;

	private Integer code;
	
	private String msg;
	
	private Integer isNext;
	
	private ArrayList<HashMap<String, Object>> dataList;

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

	public ArrayList<HashMap<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(ArrayList<HashMap<String, Object>> dataList) {
		this.dataList = dataList;
	}
}
