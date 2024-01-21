package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_ReapalQuickPay_QueryOrderResult extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6711436633459670252L;
	private String orderNo; //订单号
	
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
