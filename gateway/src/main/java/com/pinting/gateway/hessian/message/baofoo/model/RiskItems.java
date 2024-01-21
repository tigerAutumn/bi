package com.pinting.gateway.hessian.message.baofoo.model;

import java.io.Serializable;
/**
 * 协议支付风控参数-互金消金参数
 * @project gateway
 * @title RiskParameters.java
 * @author Dragon & cat
 * @date 2018-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class RiskItems implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2216933182112245364L;
	
	/*================通用参数===================*/
	/*行业类目*/
	private 	String 		goodsCategory;
	/*商户用户登录名*/
	private 	String 		userLoginId;
	/*用户邮箱*/
	private 	String 		userEmail;
	/*绑定手机号*/
	private 	String 		userMobile;
	/*用户注册姓名*/
	private 	String 		registerUserName;
	/*是否实名认证*/
	private 	String 		identifyState;
	/*用户身份证号*/
	private 	String 		userIdNo;
	/*注册时间*/
	private 	String 		registerTime;
	/*注册IP*/
	private 	String 		registerIp;
	/*持卡人姓名*/
	private 	String 		chName;
	/*持卡人身份证号*/
	private 	String 		chIdNo;
	/*持卡人银行卡号*/
	private 	String 		chCardNo;
	/*持卡人手机*/
	private 	String 		chMobile;
	/*持卡人支付IP*/
	private 	String 		chPayIp;
	/*设备指纹订单号*/
	private 	String 		deviceOrderNo;
	/*================行业参数===================*/
	/*交易类型 1-充值 2-还款 3-投标*/
	private 	String 		tradeType;
	/*用户类型 1-投资人 2-借款人*/
	private 	String 		customerType;
	/*商户会员账户是否有余额 0-否 1-是*/
	private 	String 		hasBalance;
	/*商户会员是否绑定银行卡 0-否 1-是*/
	private 	String 		hasBindCard;
	/*到期还款日 交易类型若为2 YYYYMMDDHHMMSS*/
	private 	String 		repaymentDate;
	/*借款利率 交易类型若为2 例:10.5%*/
	private 	String 		lendingRate;
	/*标的收益率 交易类型若为3 例:8.5%*/
	private 	String 		bidYields;
	/*账户前一次交易日期 0-首次 YYYYMMDDHHMMSS*/
	private 	String 		latestTradeDate;
	
	
	public String getGoodsCategory() {
		return goodsCategory;
	}
	public void setGoodsCategory(String goodsCategory) {
		this.goodsCategory = goodsCategory;
	}
	public String getUserLoginId() {
		return userLoginId;
	}
	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getRegisterUserName() {
		return registerUserName;
	}
	public void setRegisterUserName(String registerUserName) {
		this.registerUserName = registerUserName;
	}
	public String getIdentifyState() {
		return identifyState;
	}
	public void setIdentifyState(String identifyState) {
		this.identifyState = identifyState;
	}
	public String getUserIdNo() {
		return userIdNo;
	}
	public void setUserIdNo(String userIdNo) {
		this.userIdNo = userIdNo;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getRegisterIp() {
		return registerIp;
	}
	public void setRegisterIp(String registerIp) {
		this.registerIp = registerIp;
	}
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	public String getChIdNo() {
		return chIdNo;
	}
	public void setChIdNo(String chIdNo) {
		this.chIdNo = chIdNo;
	}
	public String getChCardNo() {
		return chCardNo;
	}
	public void setChCardNo(String chCardNo) {
		this.chCardNo = chCardNo;
	}
	public String getChMobile() {
		return chMobile;
	}
	public void setChMobile(String chMobile) {
		this.chMobile = chMobile;
	}
	public String getChPayIp() {
		return chPayIp;
	}
	public void setChPayIp(String chPayIp) {
		this.chPayIp = chPayIp;
	}
	public String getDeviceOrderNo() {
		return deviceOrderNo;
	}
	public void setDeviceOrderNo(String deviceOrderNo) {
		this.deviceOrderNo = deviceOrderNo;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getHasBalance() {
		return hasBalance;
	}
	public void setHasBalance(String hasBalance) {
		this.hasBalance = hasBalance;
	}
	public String getHasBindCard() {
		return hasBindCard;
	}
	public void setHasBindCard(String hasBindCard) {
		this.hasBindCard = hasBindCard;
	}
	public String getRepaymentDate() {
		return repaymentDate;
	}
	public void setRepaymentDate(String repaymentDate) {
		this.repaymentDate = repaymentDate;
	}
	public String getLendingRate() {
		return lendingRate;
	}
	public void setLendingRate(String lendingRate) {
		this.lendingRate = lendingRate;
	}
	public String getBidYields() {
		return bidYields;
	}
	public void setBidYields(String bidYields) {
		this.bidYields = bidYields;
	}
	public String getLatestTradeDate() {
		return latestTradeDate;
	}
	public void setLatestTradeDate(String latestTradeDate) {
		this.latestTradeDate = latestTradeDate;
	}
	
}
