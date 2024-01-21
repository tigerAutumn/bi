package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 理财审核通过，注册定时任务，传入参数
 * @author dingpengfeng
 * @2016-6-24 上午11:36:37
 */
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
