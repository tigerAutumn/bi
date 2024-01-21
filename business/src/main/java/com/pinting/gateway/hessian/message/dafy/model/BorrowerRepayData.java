package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.Date;

public class BorrowerRepayData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8782457586200783972L;
	
	private String customerId;
	private String borrowNo;
	private String repayerName;
	private String repayerIdCard;
	private Date repayTime;
	private double repayPrincipal;
	private String repayTransNo;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getBorrowNo() {
		return borrowNo;
	}
	public void setBorrowNo(String borrowNo) {
		this.borrowNo = borrowNo;
	}
	public String getRepayerName() {
		return repayerName;
	}
	public void setRepayerName(String repayerName) {
		this.repayerName = repayerName;
	}
	public String getRepayerIdCard() {
		return repayerIdCard;
	}
	public void setRepayerIdCard(String repayerIdCard) {
		this.repayerIdCard = repayerIdCard;
	}
	public Date getRepayTime() {
		return repayTime;
	}
	public void setRepayTime(Date repayTime) {
		this.repayTime = repayTime;
	}
	public double getRepayPrincipal() {
		return repayPrincipal;
	}
	public void setRepayPrincipal(double repayPrincipal) {
		this.repayPrincipal = repayPrincipal;
	}
	public String getRepayTransNo() {
		return repayTransNo;
	}
	public void setRepayTransNo(String repayTransNo) {
		this.repayTransNo = repayTransNo;
	}
	@Override
	public String toString() {
		return "BorrowerRepayData [customerId=" + customerId + ", borrowNo="
				+ borrowNo + ", repayerName=" + repayerName
				+ ", repayerIdCard=" + repayerIdCard + ", repayTime="
				+ repayTime + ", repayPrincipal=" + repayPrincipal
				+ ", repayTransNo=" + repayTransNo + "]";
	}

}
