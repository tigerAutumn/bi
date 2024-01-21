package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class NewBuyerResponse extends Response {

	private Integer investCount;
	
	private Double maxSingleInvestAmount;

	public Integer getInvestCount() {
		return investCount;
	}

	public void setInvestCount(Integer investCount) {
		this.investCount = investCount;
	}

	public Double getMaxSingleInvestAmount() {
		return maxSingleInvestAmount;
	}

	public void setMaxSingleInvestAmount(Double maxSingleInvestAmount) {
		this.maxSingleInvestAmount = maxSingleInvestAmount;
	}
}
