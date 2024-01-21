package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RegularBuy_Buy extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5883841572198818032L;
	private String retHtml;
	public String getRetHtml() {
		return retHtml;
	}
	public void setRetHtml(String retHtml) {
		this.retHtml = retHtml;
	}
	
}
