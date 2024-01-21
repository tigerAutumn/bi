package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * 刮刮乐活动刮奖机会
 * @author SHENGUOPING
 * @date  2017年8月21日 下午9:38:40
 */
public class ActivityScratchcardChanceVO implements Serializable {

	private static final long serialVersionUID = -8541499019315550246L;

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
