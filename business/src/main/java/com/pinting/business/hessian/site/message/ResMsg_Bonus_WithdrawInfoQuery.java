package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Bonus_WithdrawInfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1687479091706985585L;

	private Integer userId;
	private Double withdrawBalance;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getWithdrawBalance() {
		return withdrawBalance;
	}
	public void setWithdrawBalance(Double withdrawBalance) {
		this.withdrawBalance = withdrawBalance;
	}
	
}
