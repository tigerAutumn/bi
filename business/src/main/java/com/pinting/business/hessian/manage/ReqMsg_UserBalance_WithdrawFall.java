package com.pinting.business.hessian.manage;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_UserBalance_WithdrawFall extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 315883966913107310L;
	private int checkId;//审核id
	private int manageId;//管理人员id
	public int getCheckId() {
		return checkId;
	}
	public void setCheckId(int checkId) {
		this.checkId = checkId;
	}
	public int getManageId() {
		return manageId;
	}
	public void setManageId(int manageId) {
		this.manageId = manageId;
	}
}
