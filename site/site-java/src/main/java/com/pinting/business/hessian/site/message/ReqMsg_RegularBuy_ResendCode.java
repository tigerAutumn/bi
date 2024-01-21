package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_ResendCode extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3055386710719973931L;
	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
