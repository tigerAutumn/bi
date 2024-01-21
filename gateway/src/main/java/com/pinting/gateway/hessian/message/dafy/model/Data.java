package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @Project: gateway
 * @Title: queryWXAccountDetail.java
 * @author linkin
 * @date 2015-4-21 下午5:12:52
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class Data implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8991945579890292272L;
	private Date transTime;
	private String payAcNo;
	private String payeeAcNo;
	private double amount;
	private String transType;
	private String borrowerId;
	private String borrowerName;
	private String borrowerIdCard;
	private String lenderId;
	private String lenderName;
	private String lenderIdCard;
	private String productName;
	private Date createTime;
	private String borrowNote;
	private String state;
	public String getBorrowerId() {
		return borrowerId;
	}
	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}
	public String getBorrowerName() {
		return borrowerName;
	}
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	public String getBorrowerIdCard() {
		return borrowerIdCard;
	}
	public void setBorrowerIdCard(String borrowerIdCard) {
		this.borrowerIdCard = borrowerIdCard;
	}
	public String getLenderId() {
		return lenderId;
	}
	public void setLenderId(String lenderId) {
		this.lenderId = lenderId;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getLenderIdCard() {
		return lenderIdCard;
	}
	public void setLenderIdCard(String lenderIdCard) {
		this.lenderIdCard = lenderIdCard;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBorrowNote() {
		return borrowNote;
	}
	public void setBorrowNote(String borrowNote) {
		this.borrowNote = borrowNote;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public Date getTransTime() {
		return transTime;
	}
	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}
	public String getPayAcNo() {
		return payAcNo;
	}
	public void setPayAcNo(String payAcNo) {
		this.payAcNo = payAcNo;
	}
	public String getPayeeAcNo() {
		return payeeAcNo;
	}
	public void setPayeeAcNo(String payeeAcNo) {
		this.payeeAcNo = payeeAcNo;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}

}
