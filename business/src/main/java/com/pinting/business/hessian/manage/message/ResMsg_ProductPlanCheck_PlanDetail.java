package com.pinting.business.hessian.manage.message;

import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ProductPlanCheck_PlanDetail extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7696658736624554804L;
	
    private Map<String, Object> productDatas;

	public Map<String, Object> getProductDatas() {
		return productDatas;
	}

	public void setProductDatas(Map<String, Object> productDatas) {
		this.productDatas = productDatas;
	}

}
