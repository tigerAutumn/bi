package com.pinting.open.pojo.response.more;

import com.pinting.open.base.response.Response;

public class RecommendAmountResponse extends Response {
	
	/**
	 * 累计获得推荐奖励
	 */
	private Double recommendAmountTotal;

	public Double getRecommendAmountTotal() {
		return recommendAmountTotal;
	}

	public void setRecommendAmountTotal(Double recommendAmountTotal) {
		this.recommendAmountTotal = recommendAmountTotal;
	}
	
}
