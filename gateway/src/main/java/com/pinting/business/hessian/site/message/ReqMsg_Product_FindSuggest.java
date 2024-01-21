package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_FindSuggest extends ReqMsg {

	private static final long serialVersionUID = -9145463458691739322L;

	private String userType;
	
	private String productShowTerminal;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

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
