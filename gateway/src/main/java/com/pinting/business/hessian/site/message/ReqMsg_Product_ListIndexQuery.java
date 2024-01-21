package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_ListIndexQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2292442668241609747L;
	/*展示端口*/
	private String productShowTerminal;
	
	/** 用户ID */
	private Integer userId;
	
	public String getProductShowTerminal() {
		return productShowTerminal;
	}

	public void setProductShowTerminal(String productShowTerminal) {
		this.productShowTerminal = productShowTerminal;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	} 
	
}
