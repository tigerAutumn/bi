package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 用户可用余额
 * @author Dragon & cat
 * @date 2016-8-25
 */
public class ResMsg_User_UserBalanceQuery extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6668840699464711384L;
	
	/** 可用余额  账户余额 */
	private Double availableBalance;
	/*存管账户余额*/
	private Double depBalance;

	public Double getDepBalance() {
		return depBalance;
	}

	public void setDepBalance(Double depBalance) {
		this.depBalance = depBalance;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	
	
}
