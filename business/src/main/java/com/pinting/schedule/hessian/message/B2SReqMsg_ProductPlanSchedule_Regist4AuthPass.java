package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;

public class B2SReqMsg_ProductPlanSchedule_Regist4AuthPass extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5557129913869829285L;
	
	private BsProduct product;

	public BsProduct getProduct() {
		return product;
	}

	public void setProduct(BsProduct product) {
		this.product = product;
	}

}
