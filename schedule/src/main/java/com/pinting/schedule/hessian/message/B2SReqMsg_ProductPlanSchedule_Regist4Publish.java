package com.pinting.schedule.hessian.message;

import com.pinting.business.model.BsProduct;
import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 手动发布理财，重置定时任务，传入参数
 * @author dingpengfeng
 * @2016-6-24 上午11:36:12
 */
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
