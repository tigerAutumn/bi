package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_InfoQuery  extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5239309042692792540L;
	
	private int id;
	
	private int trem;
	
	private int investNum;
	
	private double rate;
	
	private Double currTotalAmount;
	
	private String bsConfigPriceLimit;
	
	private String bsConfigPriceCeiling;
	
	private String productName;
	
	private String code;

	/* 起投金额 */
	private Double minInvestAmount;

	/* 个人最高投资金额 */
	private Double maxInvestAmount;

	/* 产品限额 */
	private Double maxTotalAmount;
	
	private String propertyType;
	
	private String activityType;

	/* 个人单笔限额 */
	private Double maxSingleInvestAmount;
	
	private String propertySymbol;

	private String isSupportRedPacket;	// TRUE 支持；FALSE 不支持

	private String isSupportInterestTicket;	// TRUE 支持；FALSE 不支持

	public String getIsSupportInterestTicket() {
		return isSupportInterestTicket;
	}

	public void setIsSupportInterestTicket(String isSupportInterestTicket) {
		this.isSupportInterestTicket = isSupportInterestTicket;
	}

	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}

	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTrem() {
		return trem;
	}

	public void setTrem(int trem) {
		this.trem = trem;
	}

	public int getInvestNum() {
		return investNum;
	}

	public void setInvestNum(int investNum) {
		this.investNum = investNum;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getBsConfigPriceLimit() {
		return bsConfigPriceLimit;
	}

	public void setBsConfigPriceLimit(String bsConfigPriceLimit) {
		this.bsConfigPriceLimit = bsConfigPriceLimit;
	}

	public String getBsConfigPriceCeiling() {
		return bsConfigPriceCeiling;
	}

	public void setBsConfigPriceCeiling(String bsConfigPriceCeiling) {
		this.bsConfigPriceCeiling = bsConfigPriceCeiling;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(Double minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
	}

	public Double getMaxTotalAmount() {
		return maxTotalAmount;
	}

	public void setMaxTotalAmount(Double maxTotalAmount) {
		this.maxTotalAmount = maxTotalAmount;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Double getMaxSingleInvestAmount() {
		return maxSingleInvestAmount;
	}

	public void setMaxSingleInvestAmount(Double maxSingleInvestAmount) {
		this.maxSingleInvestAmount = maxSingleInvestAmount;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public String getIsSupportRedPacket() {
		return isSupportRedPacket;
	}

	public void setIsSupportRedPacket(String isSupportRedPacket) {
		this.isSupportRedPacket = isSupportRedPacket;
	}

	public Double getMaxInvestAmount() {
		return maxInvestAmount;
	}

	public void setMaxInvestAmount(Double maxInvestAmount) {
		this.maxInvestAmount = maxInvestAmount;
	}
}
