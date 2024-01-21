package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Home_BuyOrders extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026017142259918352L;
	
	private Integer productId;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
}
