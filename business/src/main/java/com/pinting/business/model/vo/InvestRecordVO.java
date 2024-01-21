package com.pinting.business.model.vo;

import java.util.Date;

public class InvestRecordVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7781460486287012220L;
	private String userName;
	private Double balance;
	private Date openTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	
	
}
