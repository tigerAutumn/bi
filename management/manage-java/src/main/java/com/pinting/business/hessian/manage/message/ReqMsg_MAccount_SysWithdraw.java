package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_SysWithdraw extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6151824648761113847L;
	
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
