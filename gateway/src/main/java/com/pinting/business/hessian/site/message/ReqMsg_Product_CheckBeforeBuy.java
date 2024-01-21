package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_CheckBeforeBuy extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 231704444479759042L;
	/*用户ID*/
	public Integer userId;
	/*产品ID*/
	private Integer productId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
	
}
