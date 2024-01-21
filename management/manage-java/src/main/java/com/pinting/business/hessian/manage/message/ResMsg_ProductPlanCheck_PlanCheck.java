package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ProductPlanCheck_PlanCheck extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3570074283936677578L;
	
	private BsProduct bsProduct;
	
	private String checkFlag;

	public BsProduct getBsProduct() {
		return bsProduct;
	}

	public void setBsProduct(BsProduct bsProduct) {
		this.bsProduct = bsProduct;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}
}
