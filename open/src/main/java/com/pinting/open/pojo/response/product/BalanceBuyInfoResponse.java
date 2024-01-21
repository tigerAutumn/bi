package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;
/**
 * 
 * @project open-base
 * @title BalanceBuyInfoResponse.java
 * @author Dragon & cat
 * @date 2017-4-14
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 余额购买页面信息
 */
public class BalanceBuyInfoResponse extends Response {
	
	private Integer productId; //产品ID
	
	private String term; //产品投资期限
	
	private String name; //产品名称
	
	private String rate;  //年化收益率
	
    private Double maxTotalAmount;  //限额
    
    private Double currTotalAmount;  // 累计投资额
	
	private Double minInvestAmount;
	
	private Double maxSingleInvestAmount; //个人单次最高投资金额
	
	private String propertySymbol;//资金接收方标记
	
    private String isSupportRedPacket; //是否支持红包
    
    private String returnType;  //回款类型
    
    private	String isExistRedPacket;  //是否存在可用红包（yes 是、no 否）
    
	private Integer redPacketId; //红包ID
	
	private Double full; //满额
	
	private Double subtract; //减额
	
	private String serialName; //红包名称

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Double getMaxTotalAmount() {
		return maxTotalAmount;
	}

	public void setMaxTotalAmount(Double maxTotalAmount) {
		this.maxTotalAmount = maxTotalAmount;
	}

	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}

	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

	public Double getMinInvestAmount() {
		return minInvestAmount;
	}

	public void setMinInvestAmount(Double minInvestAmount) {
		this.minInvestAmount = minInvestAmount;
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

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public String getIsExistRedPacket() {
		return isExistRedPacket;
	}

	public void setIsExistRedPacket(String isExistRedPacket) {
		this.isExistRedPacket = isExistRedPacket;
	}

	public Integer getRedPacketId() {
		return redPacketId;
	}

	public void setRedPacketId(Integer redPacketId) {
		this.redPacketId = redPacketId;
	}

	public Double getFull() {
		return full;
	}

	public void setFull(Double full) {
		this.full = full;
	}

	public Double getSubtract() {
		return subtract;
	}

	public void setSubtract(Double subtract) {
		this.subtract = subtract;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	
	
}
