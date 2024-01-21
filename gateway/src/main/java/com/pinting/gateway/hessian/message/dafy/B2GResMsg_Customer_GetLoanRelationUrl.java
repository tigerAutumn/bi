package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_Customer_GetLoanRelationUrl extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7415579975281861368L;
	private String downLoadUrl;
	public String getDownLoadUrl() {
		return downLoadUrl;
	}
	public void setDownLoadUrl(String downLoadUrl) {
		this.downLoadUrl = downLoadUrl;
	}
}
