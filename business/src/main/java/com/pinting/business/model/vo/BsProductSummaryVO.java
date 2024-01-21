package com.pinting.business.model.vo;

import com.pinting.business.model.BsProduct;

/**
 * @Project: business
 * @Title: ProductSummaryVO.java
 * @author Zhou Changzai
 * @date 2015-1-23 上午11:11:59
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BsProductSummaryVO extends BsProduct {
	private String typeName;//类型名称
	private Double maxRate;//同类型产品中最高利率
	private Integer investNum;//当前投资人数
	private Double currTotalAmount;//当前投资总和
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getMaxRate() {
		return maxRate;
	}
	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}
	public Integer getInvestNum() {
		return investNum;
	}
	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}
	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}
	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

}
