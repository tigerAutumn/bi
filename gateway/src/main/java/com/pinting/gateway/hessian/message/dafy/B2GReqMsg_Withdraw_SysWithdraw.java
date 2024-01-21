package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Withdraw_SysWithdraw extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 980417652573167734L;
	
	private Double amount;
	private String applyNo;

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
