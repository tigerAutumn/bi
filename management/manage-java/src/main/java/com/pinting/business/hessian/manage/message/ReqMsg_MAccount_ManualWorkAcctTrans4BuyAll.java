package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_ManualWorkAcctTrans4BuyAll extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6869598685449153250L;

	private String propertySymbol;

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
}
