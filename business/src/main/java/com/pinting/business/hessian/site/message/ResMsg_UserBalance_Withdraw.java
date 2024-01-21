package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @Project: business
 * @Title: ResMsg_Balance_Withdraw.java
 * @author Zhou Changzai
 * @date 2015-11-25上午9:40:17
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_UserBalance_Withdraw extends ResMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3589192323635743101L;
	
	private Date time; //该时间+1为客户提现预计到账时间
	private boolean needCheck;//是否需要审核，true需要审核
	private Date  withdrawTime; //提现申请时间
	private String orderNo; //订单号

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isNeedCheck() {
		return needCheck;
	}

	public void setNeedCheck(boolean needCheck) {
		this.needCheck = needCheck;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}


