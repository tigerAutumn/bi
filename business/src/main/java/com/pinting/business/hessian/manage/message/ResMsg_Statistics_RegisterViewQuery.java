package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_RegisterViewQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3392553701575719368L;
	
	private List<HashMap<String,Object>> userRegisterList;
	
	private int totalRows;

	public List<HashMap<String,Object>> getUserRegisterList() {
		return userRegisterList;
	}

	public void setUserRegisterList(List<HashMap<String,Object>> userRegisterList) {
		this.userRegisterList = userRegisterList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

}
