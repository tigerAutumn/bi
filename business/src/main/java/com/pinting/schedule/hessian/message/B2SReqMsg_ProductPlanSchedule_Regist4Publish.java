package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;

public class B2SReqMsg_ProductPlanSchedule_Regist4Publish extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9034538873680440081L;
	private BsProduct product;

	public BsProduct getProduct() {
		return product;
	}

	public void setProduct(BsProduct product) {
		this.product = product;
	}

}
