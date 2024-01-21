package com.pinting.business.model.vo;

import java.util.Date;

public class FinancialUserInvestDetailVO {
	private Integer rowno; //查询结果序号
    private String mobile;
    private String userName;
    private String productName;
    private Double balance;
    private Integer term;
    private Double todayIncome;
    private Date returnDay;
    private Integer dayNum;
    private Double accumulationInerest;
    
	public Integer getRowno() {
		return rowno;
	}
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Double getTodayIncome() {
		return todayIncome;
	}
	public void setTodayIncome(Double todayIncome) {
		this.todayIncome = todayIncome;
	}
	public Date getReturnDay() {
		return returnDay;
	}
	public void setReturnDay(Date returnDay) {
		this.returnDay = returnDay;
	}
	public Integer getDayNum() {
		return dayNum;
	}
	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
	}
	public Double getAccumulationInerest() {
		return accumulationInerest;
	}
	public void setAccumulationInerest(Double accumulationInerest) {
		this.accumulationInerest = accumulationInerest;
	}
    
}
