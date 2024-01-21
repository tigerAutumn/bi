package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_UserWithdraw_MyBonusWithdraw extends ResMsg {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5676815944712270043L;

	private String  withdrawTime; //提现申请时间
	
	private String orderNo; //订单号

	public String getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	
}
