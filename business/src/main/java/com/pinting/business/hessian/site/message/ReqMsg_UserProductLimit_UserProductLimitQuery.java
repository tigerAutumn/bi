package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_UserProductLimit_UserProductLimitQuery extends ReqMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 7602944036064662853L;
	
	private Integer userId;
	
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
