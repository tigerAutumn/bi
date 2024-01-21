package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Transfer_InfoTransfer extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6878529165324902532L;
	
	private double transferAmount;
	
	private Integer userId;
	
	private Integer subAccountId;
	
	public double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}
	
	
}
