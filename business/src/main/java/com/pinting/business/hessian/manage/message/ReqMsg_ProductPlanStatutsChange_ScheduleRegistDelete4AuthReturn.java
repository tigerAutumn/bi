package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4AuthReturn
		extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4450089209105807412L;
	
	private BsProduct bsProduct ;

	public BsProduct getBsProduct() {
		return bsProduct;
	}

	public void setBsProduct(BsProduct bsProduct) {
		this.bsProduct = bsProduct;
	}
	

}
