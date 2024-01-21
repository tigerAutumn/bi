package com.pinting.schedule.mongodb.model;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document( collection = "LnPayOrders")
public class LnPayOrders  {

	private Integer Id;
	
	// 字段还可以用自定义名称  
	@Field("partner_code")
	private String partnerCode;
	
	@Field("payment_channel_id")
	private Integer paymentChannelId;

	@Field("order_no")
	private String orderNo;

	private Double amount;

	@Field("ln_user_id")
	private Integer lnUserId;

	@Field("sub_account_id")
	private Integer subAccountId;

	private String channel;
	
	@Field("pay_path")
	private String payPath;

	private Integer status;

	@Field("bank_name")
	private String bankName;

	@Field("money_type")
	private Integer moneyType;

	@Field("terminal_type")
	private Integer terminalType;

	@Field("start_jnl_no")
	private Integer startJnlNo;

	@Field("end_jnl_no")
	private Integer endJnlNo;

	@Field("bank_id")
	private Integer bankId;

	@Field("bank_card_no")
	private String bankCardNo;

	@Field("account_type")
	private Integer accountType;

	@Field("trans_type")
	private String transType;

	@Field("channel_trans_type")
	private String channelTransType;

	private String mobile;
	
	@Field("id_card")
	private String idCard;

	@Field("user_name")
	private String userName;

	@Field("is_protocol_pay")
	private String isProtocolPay;

	@Field("return_code")
	private String returnCode;

	@Field("return_msg")
	private String returnMsg;

	private String note;

	@Field("create_time")
	private Date createTime;

	@Field("update_time")
	private Date updateTime;
	
	@Field("Temple")
	private ArrayList<LnRepay> lnRepayList;
	
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
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

	public ArrayList<LnRepay> getLnRepayList() {
		return lnRepayList;
	}

	public void setLnRepayList(ArrayList<LnRepay> lnRepayList) {
		this.lnRepayList = lnRepayList;
	}
}