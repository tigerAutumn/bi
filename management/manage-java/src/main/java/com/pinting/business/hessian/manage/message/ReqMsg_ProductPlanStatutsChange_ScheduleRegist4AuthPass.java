package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductPlanStatutsChange_ScheduleRegist4AuthPass extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8786911062537993977L;
	
	
	private BsProduct bsProduct  ;


	public BsProduct getBsProduct() {
		return bsProduct;
	}


	public void setBsProduct(BsProduct bsProduct) {
		this.bsProduct = bsProduct;
	}

}
