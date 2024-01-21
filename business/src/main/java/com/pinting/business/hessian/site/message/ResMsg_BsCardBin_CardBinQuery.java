package com.pinting.business.hessian.site.message;


import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsCardBin_CardBinQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4303843627480407328L;
	private String bankId;
	private String cardBin;
	private String cardBinLen;
	private String bankCardLen;
	private Date createTime;
	private Date updateTime;
	private String note;
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	public String getCardBinLen() {
		return cardBinLen;
	}
	public void setCardBinLen(String cardBinLen) {
		this.cardBinLen = cardBinLen;
	}
	public String getBankCardLen() {
		return bankCardLen;
	}
	public void setBankCardLen(String bankCardLen) {
		this.bankCardLen = bankCardLen;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

	
	
	
}
