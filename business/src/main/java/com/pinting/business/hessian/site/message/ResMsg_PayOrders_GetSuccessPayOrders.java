package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 查询处理中和成功的购买订单，有则获取第一条的状态 出参
 * @author bianyatian
 *  @2016-5-19 下午2:26:38
 */
public class ResMsg_PayOrders_GetSuccessPayOrders extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3841150783627785410L;

	private Integer buyType;//购买类型：5-支付处理中,6-支付成功,为空则表示无成功和处理中的订单

	public Integer getBuyType() {
		return buyType;
	}

	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

}
