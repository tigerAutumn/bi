package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.Date;

public class LoanRelationData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2969496874611683047L;
	private String borrowerId;
	private String borrowerName;
	private String borrowerIdCard;
	private String lenderId;
	private String lenderName;
	private String lenderIdCard;
	private String productName;
	private Double amount;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	

}
