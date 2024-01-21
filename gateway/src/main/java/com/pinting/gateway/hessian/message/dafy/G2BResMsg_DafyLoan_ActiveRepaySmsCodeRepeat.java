package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 还款预下单重发验证码短信
 * @project gateway
 * @title G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat.java
 * @author Dragon & cat
 * @date 2017-6-9
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class G2BResMsg_DafyLoan_ActiveRepaySmsCodeRepeat extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7874634865771386924L;
	
	/**
	 * 支付单号
	 */
	private		String		bgwOrderNo;

	public String getBgwOrderNo() {
		return bgwOrderNo;
	}

	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
	
	
	
}
