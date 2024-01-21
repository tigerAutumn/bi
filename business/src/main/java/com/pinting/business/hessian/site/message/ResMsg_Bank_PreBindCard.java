package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 预绑卡出参
 * @author Dragon & cat
 * @date 2016-8-28
 */
public class ResMsg_Bank_PreBindCard extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2974453449508492033L;
	
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
