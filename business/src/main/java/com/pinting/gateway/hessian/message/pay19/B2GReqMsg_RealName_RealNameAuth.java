package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.AcctAttr;
import com.pinting.gateway.hessian.message.pay19.enums.CardType;
import com.pinting.gateway.hessian.message.pay19.enums.IdType;

public class B2GReqMsg_RealName_RealNameAuth extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -408608527878278237L;
	private String mxUserId;
	private String mxReqSno;
	private Date mxReqDate;
	private String cardHolder;
	private IdType idType;
	private String idNo;
	private String mobile;
	private String pcId;
	private String bankCardNo;
	private CardType cardType;
	private AcctAttr cardAttr;
	private String cvv2;
	private String validDate;
	private String notifyUrl;
	private String remark;
	private String reserved;

	public String getMxUserId() {
		return mxUserId;
	}

	public void setMxUserId(String mxUserId) {
		this.mxUserId = mxUserId;
	}

	public String getMxReqSno() {
		return mxReqSno;
	}

	public void setMxReqSno(String mxReqSno) {
		this.mxReqSno = mxReqSno;
	}

	public Date getMxReqDate() {
		return mxReqDate;
	}

	public void setMxReqDate(Date mxReqDate) {
		this.mxReqDate = mxReqDate;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPcId() {
		return pcId;
	}

	public void setPcId(String pcId) {
		this.pcId = pcId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public AcctAttr getCardAttr() {
		return cardAttr;
	}

	public void setCardAttr(AcctAttr cardAttr) {
		this.cardAttr = cardAttr;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
