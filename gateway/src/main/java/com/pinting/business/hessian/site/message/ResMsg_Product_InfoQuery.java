package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_InfoQuery  extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1045203938729396598L;

	private int id;
	
	private int trem;
	
	private Integer term4Day;
	
	private int investNum;
	
	private double rate;
	
	private Double currTotalAmount;
	
	private String bsConfigPriceLimit;
	
	private String bsConfigPriceCeiling;
	
	private String productName;
	
	private String code;
	
	private Double minInvestAmount;
	
	private String propertySymbol;
	
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
	
	public Integer getTerm4Day() {
		if(trem < 0){
			term4Day = Math.abs(this.trem);
		}else if(trem == 12){
			term4Day = 365;
		}else{
			term4Day = trem*30;
		}
		return term4Day;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
}
