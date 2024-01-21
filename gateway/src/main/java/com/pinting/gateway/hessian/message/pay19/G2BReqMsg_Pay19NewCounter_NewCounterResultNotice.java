package com.pinting.gateway.hessian.message.pay19;


import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.AcctAttr;
import com.pinting.gateway.hessian.message.pay19.enums.CardType;
import com.pinting.gateway.hessian.message.pay19.enums.Currency;
import com.pinting.gateway.hessian.message.pay19.enums.TradeType;

public class G2BReqMsg_Pay19NewCounter_NewCounterResultNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -832600862762956792L;
	
	private String version;

	private String merchantId;

	private Date mxOrderDate;

	private String mxOrderId;

	private Double amount;

	private Currency currency;

	private String mpOrderId;

	private Date payDate;

	private String pmId;

	private String bankId;

	private CardType acctType;

	private AcctAttr acctAttr;

	private TradeType tradeType;

	private String commonRetrievedParam;

	private String result;

	private String verifystring;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getMxOrderDate() {
		return mxOrderDate;
	}

	public void setMxOrderDate(Date mxOrderDate) {
		this.mxOrderDate = mxOrderDate;
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

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public String getMpOrderId() {
		return mpOrderId;
	}

	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getPmId() {
		return pmId;
	}

	public void setPmId(String pmId) {
		this.pmId = pmId;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public CardType getAcctType() {
		return acctType;
	}

	public void setAcctType(CardType acctType) {
		this.acctType = acctType;
	}

	public AcctAttr getAcctAttr() {
		return acctAttr;
	}

	public void setAcctAttr(AcctAttr acctAttr) {
		this.acctAttr = acctAttr;
	}

	public TradeType getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeType tradeType) {
		this.tradeType = tradeType;
	}

	public String getCommonRetrievedParam() {
		return commonRetrievedParam;
	}

	public void setCommonRetrievedParam(String commonRetrievedParam) {
		this.commonRetrievedParam = commonRetrievedParam;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getVerifystring() {
		return verifystring;
	}

	public void setVerifystring(String verifystring) {
		this.verifystring = verifystring;
	}

}
