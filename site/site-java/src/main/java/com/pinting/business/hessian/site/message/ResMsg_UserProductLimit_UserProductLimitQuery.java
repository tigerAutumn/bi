package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_UserProductLimit_UserProductLimitQuery extends ResMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 7602944036064662853L;
	
	private double leftAmount;

	public double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(double leftAmount) {
		this.leftAmount = leftAmount;
	}
}
