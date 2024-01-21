package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Recharge_RechargeCommission extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3287863264551604277L;

	private Double needPayAmount; //应扣
	
	private Double actPayAmount; //实扣

	public Double getNeedPayAmount() {
		return needPayAmount;
	}

	public void setNeedPayAmount(Double needPayAmount) {
		this.needPayAmount = needPayAmount;
	}

	public Double getActPayAmount() {
		return actPayAmount;
	}

	public void setActPayAmount(Double actPayAmount) {
		this.actPayAmount = actPayAmount;
	}
	
	
	
}
