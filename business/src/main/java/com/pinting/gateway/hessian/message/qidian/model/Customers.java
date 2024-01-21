package com.pinting.gateway.hessian.message.qidian.model;

import java.io.Serializable;
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
public class Customers implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8036429528805335095L;
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
	private 	Double 		remainInvest;
	/*累计投资金额*/
	private 	Double 		totalInvest;
	/*注册时间*/
	private 	Date 		registerTime;
	/*登录时间*/
	private 	Date 		loginTime;
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
	public Double getRemainInvest() {
		return remainInvest;
	}
	public void setRemainInvest(Double remainInvest) {
		this.remainInvest = remainInvest;
	}
	public Double getTotalInvest() {
		return totalInvest;
	}
	public void setTotalInvest(Double totalInvest) {
		this.totalInvest = totalInvest;
	}
	public Date getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	public Date getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
}
