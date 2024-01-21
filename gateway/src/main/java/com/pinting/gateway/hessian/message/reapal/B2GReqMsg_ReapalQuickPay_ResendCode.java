package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_ReapalQuickPay_ResendCode extends ReqMsg {

	private static final long serialVersionUID = 3729610582283212925L;

	private String orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
