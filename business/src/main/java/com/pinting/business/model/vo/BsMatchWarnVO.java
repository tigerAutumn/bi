package com.pinting.business.model.vo;

import com.pinting.business.model.BsMatch;

import java.util.Date;

public class BsMatchWarnVO extends BsMatch {
	
	private Double matchAmount; //匹配金额
	
	private Double batchBuyAmount; //系统购买金额
	
	private Double avgAmount; //匹配评价金额
	
	private Date buyTime; //购买时间

	private String propertySymbol; //购买渠道

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public Double getMatchAmount() {
		return matchAmount;
	}
	public void setMatchAmount(Double matchAmount) {
		this.matchAmount = matchAmount;
	}
	
	public Double getBatchBuyAmount() {
		return batchBuyAmount;
	}
	public void setBatchBuyAmount(Double batchBuyAmount) {
		this.batchBuyAmount = batchBuyAmount;
	}
	public Double getAvgAmount() {
		return avgAmount;
	}
	public void setAvgAmount(Double avgAmount) {
		this.avgAmount = avgAmount;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

}
