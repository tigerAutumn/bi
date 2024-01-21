package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.AcctAttr;
import com.pinting.gateway.hessian.message.pay19.enums.CardType;
import com.pinting.gateway.hessian.message.pay19.enums.Currency;
import com.pinting.gateway.hessian.message.pay19.enums.PayType;
import com.pinting.gateway.hessian.message.pay19.enums.PersistHandling;
import com.pinting.gateway.hessian.message.pay19.enums.TradeType;

public class B2GReqMsg_Pay4Another_NewMerchantDf extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3251780869495777884L;
	private Date reqTime;
	private String mxOrderId;
	private Double amount;
	private String cardHolder;
	private String bankCardId;
	private AcctAttr accountType;
	private CardType cardType;
	private String bankCode;
	private String subBankName;
	private String provinceId;
	private String cityId;
	private String alliedBankCode;
	private PayType payType;
	private String tradeDesc;
	private String notifyUrl;
	private Currency currency;
	private TradeType tradeType;
	private String mxReserved;
	private PersistHandling persistHandling;
	private String persistTimeOut = "43200"; // 30天
	private String securityInfo = "1"; // 默认为1

	public Date getReqTime() {
		return reqTime;
	}

	public void setReqTime(Date reqTime) {
		this.reqTime = reqTime;
	}

	public String getMxOrderId() {
		return mxOrderId;
	}

	public void setMxOrderId(String mxOrderId) {
		this.mxOrderId = mxOrderId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getBankCardId() {
		return bankCardId;
	}

	public void setBankCardId(String bankCardId) {
		this.bankCardId = bankCardId;
	}

	public AcctAttr getAccountType() {
		return accountType;
	}

	public void setAccountType(AcctAttr accountType) {
		this.accountType = accountType;
	}

	public CardType getCardType() {
		return cardType;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSubBankName() {
		return subBankName;
	}

	public void setSubBankName(String subBankName) {
		this.subBankName = subBankName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getAlliedBankCode() {
		return alliedBankCode;
	}

	public void setAlliedBankCode(String alliedBankCode) {
		this.alliedBankCode = alliedBankCode;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public String getMxReserved() {
		return mxReserved;
	}

	public void setMxReserved(String mxReserved) {
		this.mxReserved = mxReserved;
	}

	public PersistHandling getPersistHandling() {
		return persistHandling;
	}

	public void setPersistHandling(PersistHandling persistHandling) {
		this.persistHandling = persistHandling;
	}

	public String getPersistTimeOut() {
		return persistTimeOut;
	}

	public void setPersistTimeOut(String persistTimeOut) {
		this.persistTimeOut = persistTimeOut;
	}

	public String getSecurityInfo() {
		return securityInfo;
	}

	public void setSecurityInfo(String securityInfo) {
		this.securityInfo = securityInfo;
	}

}
