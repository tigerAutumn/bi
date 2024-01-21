package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_ReapalQuickCMB extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4118534496600793651L;
	
	private String orderNo;
	private Integer productId;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	

}
