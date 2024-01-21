package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_OrderTrocessing extends ReqMsg {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6126380283908566071L;
	private String orderNo;
	private String errorText;
	
	public String getErrorText() {
		return errorText;
	}
	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
