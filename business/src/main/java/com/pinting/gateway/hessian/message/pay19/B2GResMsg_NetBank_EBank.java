package com.pinting.gateway.hessian.message.pay19;


import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_NetBank_EBank extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1040654298807012911L;
	private String htmlStr;
	public String getHtmlStr() {
		return htmlStr;
	}
	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}


}
