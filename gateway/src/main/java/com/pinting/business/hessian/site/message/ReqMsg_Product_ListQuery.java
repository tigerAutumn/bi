package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_ListQuery extends ReqMsg {

	private static final long serialVersionUID = 7077895019443776386L;
	
	private String userType;
	
	private String productShowTerminal;
	
	private Integer page;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getProductShowTerminal() {
		return productShowTerminal;
	}

	public void setProductShowTerminal(String productShowTerminal) {
		this.productShowTerminal = productShowTerminal;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
