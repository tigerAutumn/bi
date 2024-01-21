package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 刮刮乐活动是否有刮奖机会
 * @author SHENGUOPING
 * @date  2017年8月21日 下午5:31:36
 */
public class ResMsg_ScratchcardActivity_HasScratchChance extends ResMsg {

	private static final long serialVersionUID = 5845360184215548791L;
	
	/** 是否有刮奖机会 */
	private boolean hasScratchChance;
	
	/** 年化投资金额 */
	private Double yearsInvestAmount; 
	
	public boolean isHasScratchChance() {
		return hasScratchChance;
	}

	public void setHasScratchChance(boolean hasScratchChance) {
		this.hasScratchChance = hasScratchChance;
	}

	public Double getYearsInvestAmount() {
		return yearsInvestAmount;
	}

	public void setYearsInvestAmount(Double yearsInvestAmount) {
		this.yearsInvestAmount = yearsInvestAmount;
	}

}
