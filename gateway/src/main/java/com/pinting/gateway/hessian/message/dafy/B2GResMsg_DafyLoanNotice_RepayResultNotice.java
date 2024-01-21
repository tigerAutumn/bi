package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_DafyLoanNotice_RepayResultNotice extends ResMsg {

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
