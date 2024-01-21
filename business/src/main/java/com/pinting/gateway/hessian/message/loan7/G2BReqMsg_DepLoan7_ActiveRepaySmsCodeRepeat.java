package com.pinting.gateway.hessian.message.loan7;

import org.hibernate.validator.constraints.NotEmpty;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 还款预下单重发验证码短信
 * @project gateway
 * @title G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BReqMsg_DepLoan7_ActiveRepaySmsCodeRepeat extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3121691126353215741L;
	
	/*还款单号 	原预下单还款单号*/
	@NotEmpty(message="orderNo为空")
	private		String		orderNo;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
