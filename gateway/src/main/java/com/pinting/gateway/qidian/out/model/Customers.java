package com.pinting.gateway.qidian.out.model;

import java.util.Date;

/**
 * 客户信息同步
 * @project gateway
 * @title customers.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  客户信息列表
 */
public class Customers {
	/*七店店主id*/
	private 	String 		franchiseeId;
	/*客户id*/
	private 	String 		customerId;
	/*客户姓名*/
	private 	String 		customerName;
	/*客户手机号*/
	private 	String 		customerMobile;
	/*客户层级*/
	private 	Integer 		customerLevel;
	/*实名认证状态*/
	private 	String 		realNameStatus;
	/*客户身份证号*/
	private 	String 		idCardNo;
	/*客户在投金额*/
	private 	Long 		remainInvest;
	/*累计投资金额*/
	private 	Long 		totalInvest;
	/*注册时间*/
	private 	String 		registerTime;
	/*登录时间*/
	private 	String 		loginTime;
	public String getFranchiseeId() {
		return franchiseeId;
	}
	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public Integer getCustomerLevel() {
		return customerLevel;
	}
	public void setCustomerLevel(Integer customerLevel) {
		this.customerLevel = customerLevel;
	}
	public String getRealNameStatus() {
		return realNameStatus;
	}
	public void setRealNameStatus(String realNameStatus) {
		this.realNameStatus = realNameStatus;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public Long getRemainInvest() {
		return remainInvest;
	}
	public void setRemainInvest(Long remainInvest) {
		this.remainInvest = remainInvest;
	}
	public Long getTotalInvest() {
		return totalInvest;
	}
	public void setTotalInvest(Long totalInvest) {
		this.totalInvest = totalInvest;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
}
