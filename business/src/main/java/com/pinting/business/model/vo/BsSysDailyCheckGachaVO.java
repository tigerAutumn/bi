package com.pinting.business.model.vo;

/**
 * 管理台-对账结果VO
 * @author SHENGUOPING
 * @date  2018年4月23日 上午9:47:21
 */
public class BsSysDailyCheckGachaVO {

	// 成功入账金额
	private Double succInAmount; 
	
	// 成功入账金额
	private Double succInCount;
	
	// 成功出账金额
	private Double succOutAmount; 
	
	// 成功入账金额
	private Double succOutCount;

	public Double getSuccInAmount() {
		return succInAmount;
	}

	public void setSuccInAmount(Double succInAmount) {
		this.succInAmount = succInAmount;
	}

	public Double getSuccInCount() {
		return succInCount;
	}

	public void setSuccInCount(Double succInCount) {
		this.succInCount = succInCount;
	}

	public Double getSuccOutAmount() {
		return succOutAmount;
	}

	public void setSuccOutAmount(Double succOutAmount) {
		this.succOutAmount = succOutAmount;
	}

	public Double getSuccOutCount() {
		return succOutCount;
	}

	public void setSuccOutCount(Double succOutCount) {
		this.succOutCount = succOutCount;
	}
	
}
