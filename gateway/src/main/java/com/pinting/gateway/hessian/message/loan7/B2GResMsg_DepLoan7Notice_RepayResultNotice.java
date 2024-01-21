package com.pinting.gateway.hessian.message.loan7;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 
 * @project gateway
 * @title B2GResMsg_DepLoan7Notice_RepayResultNotice.java
 * @author Dragon & cat
 * @date 2017-12-12
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class B2GResMsg_DepLoan7Notice_RepayResultNotice extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156882697078071015L;
	
	private String responseTime;


	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}


}
