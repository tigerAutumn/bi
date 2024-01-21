package com.pinting.business.model.vo;

public class BsInvestProportionVO {
	
	private String productType; //产品类型:港湾计划（GW），赞分期（ZAN）
	
	private Double investAmount; //在投金额
	
	private Integer investNum; //在投笔数
	
	private Double proportionRate; //占比

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Double getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Double investAmount) {
		this.investAmount = investAmount;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public Double getProportionRate() {
		return proportionRate;
	}

	public void setProportionRate(Double proportionRate) {
		this.proportionRate = proportionRate;
	}
	

}
