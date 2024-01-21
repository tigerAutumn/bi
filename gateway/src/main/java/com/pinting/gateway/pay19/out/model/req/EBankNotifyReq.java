package com.pinting.gateway.pay19.out.model.req;

public class EBankNotifyReq extends BaseReq{

	private static final long serialVersionUID = 8510851555879397997L;
	
	private String version;
	
	private String merchantId;

	private String mxOrderDate;
	
	private String mxOrderId;
	
	private String amount;
	
	private String currency;
	
	private String mpOrderId;
	
	private String payDate;
	
	private String pmId;
	
	private String bankId;
	
	private String acctType;
	
	private String acctAttr;
	
	private String tradeType;
	
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

	public String getMxOrderDate() {
		return mxOrderDate;
	}

	public void setMxOrderDate(String mxOrderDate) {
		this.mxOrderDate = mxOrderDate;
	}

	public String getMxOrderId() {
		return mxOrderId;
	}

	public void setMxOrderId(String mxOrderId) {
		this.mxOrderId = mxOrderId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMpOrderId() {
		return mpOrderId;
	}

	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
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

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctAttr() {
		return acctAttr;
	}

	public void setAcctAttr(String acctAttr) {
		this.acctAttr = acctAttr;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
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

	@Override
	public String toString() {
		return "新网银支付接口(异步推送数据)： 【version=" + version + ", merchantId=" + merchantId + ", mxOrderDate=" + mxOrderDate
				+ ", mxOrderId=" + mxOrderId + ", amount=" + amount + ", currency=" + currency + ", mpOrderId="
				+ mpOrderId + ", payDate=" + payDate + ", pmId=" + pmId + ", bankId=" + bankId + ", acctType="
				+ acctType + ", acctAttr=" + acctAttr + ", tradeType=" + tradeType + ", commonRetrievedParam="
				+ commonRetrievedParam + ", result=" + result + ", verifystring=" + verifystring + "】";
	}

}
