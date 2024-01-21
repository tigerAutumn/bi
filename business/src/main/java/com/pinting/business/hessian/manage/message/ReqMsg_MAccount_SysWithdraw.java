package com.pinting.business.hessian.manage.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_SysWithdraw extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6151824648761113847L;
	@NotNull(message="金额不能为空")
	private Double amount;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	
}
