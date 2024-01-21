package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @ClassName: NewCounterEBankReq 
 * @Package com.pinting.gateway.pay19.out.model.req
 * @Description: 19付新网银接口userPhone
 * @author lenovo
 * @date 2015年11月4日 下午2:19:32 
 *
 */
public class EBankReq extends NewCounterBaseReq {

	private static final long serialVersionUID = -5344869066417505092L;

	private String mxOrderDate;
	
	private String mxOrderId;
	
	private String amount;
	
	private String currency;
	
	private String mxUserIp;
	
	private String retUrl;
	
	private String notifyUrl;
	
	private String bankId;
	
	private String acctType;
	
	private String acctAttr;
	
	private String orderPname;
	
	private String orderPdesc;
	
	private String userName;
	
	private String userPhone;
	
	private String userMobile;
	
	private String userEmail;
	
	private String mxHomePage;
	
	private String pageType;
	
	private String innerFlag;
	
	private String mxUserId;
	
	private String mxBusiAppId;
	
	private String mxGoodsDisplayUrl;
	
	private String overtimeInterval;
	
	private String orderDesc;
	
	private String mxDesc;
	
	private String tradeType;
	
	private String tradeDesc;
	
	private String commonRetrievedParam;
	
	private String commonBusiExpendParam;
	
	private String reserved;

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

	public String getMxUserIp() {
		return mxUserIp;
	}

	public void setMxUserIp(String mxUserIp) {
		this.mxUserIp = mxUserIp;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
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

	public String getOrderPname() {
		return orderPname;
	}

	public void setOrderPname(String orderPname) {
		this.orderPname = orderPname;
	}

	public String getOrderPdesc() {
		return orderPdesc;
	}

	public void setOrderPdesc(String orderPdesc) {
		this.orderPdesc = orderPdesc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getMxHomePage() {
		return mxHomePage;
	}

	public void setMxHomePage(String mxHomePage) {
		this.mxHomePage = mxHomePage;
	}

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getInnerFlag() {
		return innerFlag;
	}

	public void setInnerFlag(String innerFlag) {
		this.innerFlag = innerFlag;
	}

	public String getMxUserId() {
		return mxUserId;
	}

	public void setMxUserId(String mxUserId) {
		this.mxUserId = mxUserId;
	}

	public String getMxBusiAppId() {
		return mxBusiAppId;
	}

	public void setMxBusiAppId(String mxBusiAppId) {
		this.mxBusiAppId = mxBusiAppId;
	}

	public String getMxGoodsDisplayUrl() {
		return mxGoodsDisplayUrl;
	}

	public void setMxGoodsDisplayUrl(String mxGoodsDisplayUrl) {
		this.mxGoodsDisplayUrl = mxGoodsDisplayUrl;
	}

	public String getOvertimeInterval() {
		return overtimeInterval;
	}

	public void setOvertimeInterval(String overtimeInterval) {
		this.overtimeInterval = overtimeInterval;
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

	public String getCommonRetrievedParam() {
		return commonRetrievedParam;
	}

	public void setCommonRetrievedParam(String commonRetrievedParam) {
		this.commonRetrievedParam = commonRetrievedParam;
	}

	public String getCommonBusiExpendParam() {
		return commonBusiExpendParam;
	}

	public void setCommonBusiExpendParam(String commonBusiExpendParam) {
		this.commonBusiExpendParam = commonBusiExpendParam;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
}
