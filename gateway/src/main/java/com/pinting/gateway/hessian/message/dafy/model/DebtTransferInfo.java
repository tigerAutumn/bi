package com.pinting.gateway.hessian.message.dafy.model;

import java.io.Serializable;
import java.util.Date;

public class DebtTransferInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3999742261613911562L;
	/*债权出让人姓名*/
	private		String		outUserName;
	/*债权出让人身份证号*/
	private		String		outIdCard;
	/*转让金额*/
	private		Long		transAmount;
	/*剩余期数*/
	private		Integer		peroid;
	/*债权受让人姓名*/
	private		String 		inUserName;
	/*债权受让人身份证号*/
	private		String		inIdCard;
	/*转让时间*/
	private		Date		transTime;
	/*备注*/
	private		String		note;


	public String getOutUserName() {
		return outUserName;
	}

	public void setOutUserName(String outUserName) {
		this.outUserName = outUserName;
	}

	public String getOutIdCard() {
		return outIdCard;
	}

	public void setOutIdCard(String outIdCard) {
		this.outIdCard = outIdCard;
	}

	public Long getTransAmount() {
		return transAmount;
	}

	public void setTransAmount(Long transAmount) {
		this.transAmount = transAmount;
	}

	public Integer getPeroid() {
		return peroid;
	}

	public void setPeroid(Integer peroid) {
		this.peroid = peroid;
	}

	public String getInUserName() {
		return inUserName;
	}

	public void setInUserName(String inUserName) {
		this.inUserName = inUserName;
	}

	public String getInIdCard() {
		return inIdCard;
	}

	public void setInIdCard(String inIdCard) {
		this.inIdCard = inIdCard;
	}
	

	public Date getTransTime() {
		return transTime;
	}

	public void setTransTime(Date transTime) {
		this.transTime = transTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	
}
