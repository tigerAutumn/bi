package com.pinting.business.model;

import com.pinting.business.model.vo.PageInfoObject;

import java.util.Date;

public class LnPayOrders  extends PageInfoObject  {
    private Integer id;

	private Integer paymentChannelId;

	private String orderNo;

	private Double amount;

	private String partnerCode;

	private Integer lnUserId;

	private Integer subAccountId;

	private String channel;

	private String payPath;

	private Integer status;

	private String bankName;

	private Integer moneyType;

	private Integer terminalType;

	private Integer startJnlNo;

	private Integer endJnlNo;

	private Integer bankId;

	private String bankCardNo;

	private Integer accountType;

	private String transType;

	private String channelTransType;

	private String mobile;

	private String idCard;

	private String userName;

	private String isProtocolPay;

	private String returnCode;

	private String returnMsg;

	private String note;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(Integer paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Integer getLnUserId() {
		return lnUserId;
	}

	public void setLnUserId(Integer lnUserId) {
		this.lnUserId = lnUserId;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPayPath() {
		return payPath;
	}

	public void setPayPath(String payPath) {
		this.payPath = payPath;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getMoneyType() {
		return moneyType;
	}

	public void setMoneyType(Integer moneyType) {
		this.moneyType = moneyType;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getStartJnlNo() {
		return startJnlNo;
	}

	public void setStartJnlNo(Integer startJnlNo) {
		this.startJnlNo = startJnlNo;
	}

	public Integer getEndJnlNo() {
		return endJnlNo;
	}

	public void setEndJnlNo(Integer endJnlNo) {
		this.endJnlNo = endJnlNo;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankCardNo() {
		return bankCardNo;
	}

	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getChannelTransType() {
		return channelTransType;
	}

	public void setChannelTransType(String channelTransType) {
		this.channelTransType = channelTransType;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsProtocolPay() {
		return isProtocolPay;
	}

	public void setIsProtocolPay(String isProtocolPay) {
		this.isProtocolPay = isProtocolPay;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

}