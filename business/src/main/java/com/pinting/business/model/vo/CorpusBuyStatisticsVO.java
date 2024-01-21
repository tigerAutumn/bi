package com.pinting.business.model.vo;

import java.util.Date;

public class CorpusBuyStatisticsVO {
	
	private Integer rowno; //查询结果序号

	private Integer userId; //用户id
	
	private String userName; //用户姓名
	
	private String mobile; //用户手机号
	
	private String orderNo; //购买订单号
	
	private Date time; //购买时间
	
	private String customerCode; //客户代码
	
	private Double amount; //购买金额(除掉红包)
	
	private Double balance; //投资本金
	
	private Double userInterest; 
	
	private Double financeInterest; 
	
	
	private Double corpusBuyTotalAmount;
	
	private Double corpusBuyTotalFinanceInterest;
	
	private Double corpusBuyTotalUserInterest;
	
	private String propertyCode; //融资客户代码
	
	private String propertyName; //资产合作方

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(Double userInterest) {
		this.userInterest = userInterest;
	}

	public Double getFinanceInterest() {
		return financeInterest;
	}

	public void setFinanceInterest(Double financeInterest) {
		this.financeInterest = financeInterest;
	}

	public Double getCorpusBuyTotalAmount() {
		return corpusBuyTotalAmount;
	}

	public void setCorpusBuyTotalAmount(Double corpusBuyTotalAmount) {
		this.corpusBuyTotalAmount = corpusBuyTotalAmount;
	}

	public Double getCorpusBuyTotalFinanceInterest() {
		return corpusBuyTotalFinanceInterest;
	}

	public void setCorpusBuyTotalFinanceInterest(
			Double corpusBuyTotalFinanceInterest) {
		this.corpusBuyTotalFinanceInterest = corpusBuyTotalFinanceInterest;
	}

	public Double getCorpusBuyTotalUserInterest() {
		return corpusBuyTotalUserInterest;
	}

	public void setCorpusBuyTotalUserInterest(Double corpusBuyTotalUserInterest) {
		this.corpusBuyTotalUserInterest = corpusBuyTotalUserInterest;
	}

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
}
