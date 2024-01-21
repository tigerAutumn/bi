package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_FindRate extends ReqMsg {

	private static final long serialVersionUID = -2281251374173817451L;

	private String userType;
	
	private String productShowTerminal;

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getProductShowTerminal() {
		return productShowTerminal;
	}

	public void setProductShowTerminal(String productShowTerminal) {
		this.productShowTerminal = productShowTerminal;
	}

}
