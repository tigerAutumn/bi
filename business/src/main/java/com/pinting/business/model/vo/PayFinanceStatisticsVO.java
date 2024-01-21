package com.pinting.business.model.vo;

import java.util.Date;

public class PayFinanceStatisticsVO {
	
	private Integer rowno; //查询结果序号

	private Integer userId; //用户id
	
	private String userName; //用户姓名
	
	private String mobile; //用户手机号
	
	private String orderNo; //购买订单号
	
	private Date time; //购买时间
	
	private String customerCode; //客户代码
	
	private Double amount; //购买金额(除掉红包)
	
	private Double balance; //购买金额(包含红包)
	
	private Double financeInterest;  //融资客户应付利息
	
	private Double financeTotalAmount;  //融资客户本息合计
	
	private Double userInterest; //应付投资客户利息
	
	private Double interestIncome; //公司利息收入
	
	
	private Double totalBalance;  //购买总金额
	
	private Double totalFinanceInterest; //融资客户应付利息合计
	
	private Double totalUserInterest; //应付投资客户利息合计
	
	private String propertyCode;//资金方标识

	private String lnUserName; //融资客户名称

	private String partnerBusinessFlag; //借款产品标识

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

	public Double getFinanceInterest() {
		return financeInterest;
	}

	public void setFinanceInterest(Double financeInterest) {
		this.financeInterest = financeInterest;
	}

	public Double getFinanceTotalAmount() {
		return financeTotalAmount;
	}

	public void setFinanceTotalAmount(Double financeTotalAmount) {
		this.financeTotalAmount = financeTotalAmount;
	}

	public Double getUserInterest() {
		return userInterest;
	}

	public void setUserInterest(Double userInterest) {
		this.userInterest = userInterest;
	}

	public Double getInterestIncome() {
		return interestIncome;
	}

	public void setInterestIncome(Double interestIncome) {
		this.interestIncome = interestIncome;
	}

	public Double getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(Double totalBalance) {
		this.totalBalance = totalBalance;
	}

	public Double getTotalFinanceInterest() {
		return totalFinanceInterest;
	}

	public void setTotalFinanceInterest(Double totalFinanceInterest) {
		this.totalFinanceInterest = totalFinanceInterest;
	}

	public Double getTotalUserInterest() {
		return totalUserInterest;
	}

	public void setTotalUserInterest(Double totalUserInterest) {
		this.totalUserInterest = totalUserInterest;
	}

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public String getLnUserName() {
		return lnUserName;
	}

	public void setLnUserName(String lnUserName) {
		this.lnUserName = lnUserName;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
}
