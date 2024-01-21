package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductPlanCheck_PlanDetail extends ReqMsg {

	/**
	 * 
	 */
	 
	private static final long serialVersionUID = -685350597000766006L;
	private Integer productId;
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
