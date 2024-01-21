package com.pinting.gateway.pay19.out.model.req;

public class HoldingOrderReq extends HoldingOrderBaseReq {

	private static final long serialVersionUID = 5370704872669092030L;
	
	private String contractNo;
	
	private String pcId;
	
	private String bankCardNum;
	
	private String cardHolder;
	
	private String idCardNum;
	
	private String userMobile;
	
	private String amount;
	
	private String currency;
	
	private String notifyUrl;
	
	private String commonRetrievedParam;
	
	private String commonBusiExtendParam;
	
	private String orderDesc;
	
	private String mxDesc;
	
	private String tradeType;
	
	private String tradeDesc;
	
	private String mxGoodsName;
	
	private String mxGoodsType;
	
	private String reserved;

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getPcId() {
		return pcId;
	}

	public void setPcId(String pcId) {
		this.pcId = pcId;
	}

	public String getBankCardNum() {
		return bankCardNum;
	}

	public void setBankCardNum(String bankCardNum) {
		this.bankCardNum = bankCardNum;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getIdCardNum() {
		return idCardNum;
	}

	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
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

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getCommonRetrievedParam() {
		return commonRetrievedParam;
	}

	public void setCommonRetrievedParam(String commonRetrievedParam) {
		this.commonRetrievedParam = commonRetrievedParam;
	}

	public String getCommonBusiExtendParam() {
		return commonBusiExtendParam;
	}

	public void setCommonBusiExtendParam(String commonBusiExtendParam) {
		this.commonBusiExtendParam = commonBusiExtendParam;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getMxDesc() {
		return mxDesc;
	}

	public void setMxDesc(String mxDesc) {
		this.mxDesc = mxDesc;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getMxGoodsName() {
		return mxGoodsName;
	}

	public void setMxGoodsName(String mxGoodsName) {
		this.mxGoodsName = mxGoodsName;
	}

	public String getMxGoodsType() {
		return mxGoodsType;
	}

	public void setMxGoodsType(String mxGoodsType) {
		this.mxGoodsType = mxGoodsType;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}

}
