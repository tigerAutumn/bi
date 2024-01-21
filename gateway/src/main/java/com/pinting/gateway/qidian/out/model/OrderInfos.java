package com.pinting.gateway.qidian.out.model;

import java.util.Date;

/**
 * 
 * @project gateway
 * @title OrderInfos.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class OrderInfos {
	/*订单编号*/
	private 	String 		orderNo;
	/*客户id*/
	private 	String 		customerId;
	/*产品合作资产方*/
	private 	String 		partnerName;
	/*投资产品id*/
	private 	Integer 		productId;
	/*产品名称*/
	private 	String 		productName;
	/*产品期限 单位：天*/
	private 	Integer 		productTerm;
	/*投资状态 INVESTING 投资中;FINISH已结算*/
	private 	String 		investStatus;
	/*投资状态原因描述*/
	private 	String 		investStatusDesc;
	/*回款方式 FINISH_RETURN_ALL 到期还本付息
	AVERAGE_CAPITAL_PLUS_INTEREST 等额本息*/
	private 	String 		returnType;
	/*产品基础收益率 100=1%*/
	private 	Integer 		baseRate;
	/*是否加息*/
	private 	String 		isRaise;
	/*加息利率 100=1%*/
	private 	Integer 		raiseRate;
	/*初始投资金额/预约投资金额 单位：分*/
	private 	Long 		openBalance;
	/*在投金额 单位：分*/
	private 	Long 		balance;
	/*预计收益金额 单位：分*/
	private 	Long 		expectYield;
	/*投资开始时间/预约成功时间*/
	private 	String 		openTime;
	/*计息日*/
	private 	String 		interestBeginDate;
	/*更新时间*/
	private 	String 		updateTime;
	/*预计回款日*/
	private 	String 		planDate;
	/*客户实际收益金额 单位：分*/
	private 	Long 		yield;
	/*赎回日期*/
	private 	String 		returnDate;
	/*是否优惠 （Y 是;N 否）*/
	private 	String 		isDiscount;
	/*优惠金额 单位：分*/
	private 	Long 		discountAmount;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getPartnerName() {
		return partnerName;
	}
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Integer getProductTerm() {
		return productTerm;
	}
	public void setProductTerm(Integer productTerm) {
		this.productTerm = productTerm;
	}
	public String getInvestStatus() {
		return investStatus;
	}
	public void setInvestStatus(String investStatus) {
		this.investStatus = investStatus;
	}
	public String getInvestStatusDesc() {
		return investStatusDesc;
	}
	public void setInvestStatusDesc(String investStatusDesc) {
		this.investStatusDesc = investStatusDesc;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Integer getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(Integer baseRate) {
		this.baseRate = baseRate;
	}
	public String getIsRaise() {
		return isRaise;
	}
	public void setIsRaise(String isRaise) {
		this.isRaise = isRaise;
	}
	public Integer getRaiseRate() {
		return raiseRate;
	}
	public void setRaiseRate(Integer raiseRate) {
		this.raiseRate = raiseRate;
	}
	public Long getOpenBalance() {
		return openBalance;
	}
	public void setOpenBalance(Long openBalance) {
		this.openBalance = openBalance;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Long getExpectYield() {
		return expectYield;
	}
	public void setExpectYield(Long expectYield) {
		this.expectYield = expectYield;
	}
	
	public String getOpenTime() {
		return openTime;
	}
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	public String getInterestBeginDate() {
		return interestBeginDate;
	}
	public void setInterestBeginDate(String interestBeginDate) {
		this.interestBeginDate = interestBeginDate;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getPlanDate() {
		return planDate;
	}
	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}
	public Long getYield() {
		return yield;
	}
	public void setYield(Long yield) {
		this.yield = yield;
	}
	
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getIsDiscount() {
		return isDiscount;
	}
	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}
	public Long getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
	
}
