package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RegularBuy_BalanceBuy extends ResMsg {

	private static final long serialVersionUID = -7319381671783114402L;
	
	private  String   isAutoRedPocket = "no"; //是否发放了自动红包

	public String getIsAutoRedPocket() {
		return isAutoRedPocket;
	}

	public void setIsAutoRedPocket(String isAutoRedPocket) {
		this.isAutoRedPocket = isAutoRedPocket;
	}
	
	
}
