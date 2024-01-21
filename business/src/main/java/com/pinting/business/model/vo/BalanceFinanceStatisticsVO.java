package com.pinting.business.model.vo;

import java.util.Date;

public class BalanceFinanceStatisticsVO {
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
	
  	private Double totalBalance; 
  	
  	private Double totalFinanceInterest; 
  	
	private String propertyCode;//资金方标识
	
	private String note; //备注是否逾期
	
	private Integer loanId; //借款id
	
	private Double sumFinanceInterest; //每期总应付利息
	
	private Double sumFinanceInterestAll; //每笔借款总应付利息
	
	private Integer serialId; //期次
	
	private Integer period; //借款期数
	
	private Double totalAmount; //总本金
	
	private Integer isMin; //融资客户应付利息是否是最小的
	
	private Double transAmountTotal; //宝付账户变动金额
  	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Double getSumFinanceInterest() {
		return sumFinanceInterest;
	}

	public void setSumFinanceInterest(Double sumFinanceInterest) {
		this.sumFinanceInterest = sumFinanceInterest;
	}

	public Double getSumFinanceInterestAll() {
		return sumFinanceInterestAll;
	}

	public void setSumFinanceInterestAll(Double sumFinanceInterestAll) {
		this.sumFinanceInterestAll = sumFinanceInterestAll;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

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

	public String getPropertyCode() {
		return propertyCode;
	}

	public void setPropertyCode(String propertyCode) {
		this.propertyCode = propertyCode;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getIsMin() {
		return isMin;
	}

	public void setIsMin(Integer isMin) {
		this.isMin = isMin;
	}

	public Double getTransAmountTotal() {
		return transAmountTotal;
	}

	public void setTransAmountTotal(Double transAmountTotal) {
		this.transAmountTotal = transAmountTotal;
	}
}
