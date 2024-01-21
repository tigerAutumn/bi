package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 已获得推荐奖励
 * @author SHENGP
 * @date  2017年7月20日 下午5:32:11
 */
public class ResMsg_User_RecommendBonus extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4781156902463354259L;
	
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
