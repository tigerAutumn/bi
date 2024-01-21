package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Reconciliation_CheckAccount2Dafy extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4147762559769775533L;
	private List<HashMap<String, Object>> list;
	public List<HashMap<String, Object>> getList() {
		return list;
	}
	public void setList(List<HashMap<String, Object>> list) {
		this.list = list;
	}

}
