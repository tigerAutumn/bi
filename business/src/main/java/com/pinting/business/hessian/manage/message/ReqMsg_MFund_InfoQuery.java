package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MFund_InfoQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2093487624597705743L;

	private String operateType;
	
	private int id;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
