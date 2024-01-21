package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_RecommendAmount extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1243021609689602725L;

	private Double recommendAmountTotal;

	public Double getRecommendAmountTotal() {
		return recommendAmountTotal;
	}

	public void setRecommendAmountTotal(Double recommendAmountTotal) {
		this.recommendAmountTotal = recommendAmountTotal;
	}
	
}
