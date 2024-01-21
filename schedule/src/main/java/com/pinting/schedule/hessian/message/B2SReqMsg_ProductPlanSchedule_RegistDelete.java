package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 删除理财定时任务，传入参数
 * @author dingpengfeng
 * @2016-6-24 上午11:35:37
 */
public class B2SReqMsg_ProductPlanSchedule_RegistDelete extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1416511069870636870L;
	private BsProduct product;

	public BsProduct getProduct() {
		return product;
	}

	public void setProduct(BsProduct product) {
		this.product = product;
	}

}
