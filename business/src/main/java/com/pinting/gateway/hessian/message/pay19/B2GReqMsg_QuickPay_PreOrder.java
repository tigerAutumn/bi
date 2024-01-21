package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.AcctAttr;
import com.pinting.gateway.hessian.message.pay19.enums.AcctType;
import com.pinting.gateway.hessian.message.pay19.enums.Currency;
import com.pinting.gateway.hessian.message.pay19.enums.GoodsType;
import com.pinting.gateway.hessian.message.pay19.enums.IdType;
import com.pinting.gateway.hessian.message.pay19.enums.IsFixBindInfo;
import com.pinting.gateway.hessian.message.pay19.enums.IsSaveBind;
import com.pinting.gateway.hessian.message.pay19.enums.TradeType;

public class B2GReqMsg_QuickPay_PreOrder extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9035319975720012352L;
	private String userId;
	private String bindSNo;
	private String orderId;
	private Date orderDate;
	private Double amount;
	private String orderPName;
	private String orderPDesc;
	private String pcId;
	private String bankCardNum;
	private String cvv2;
	private String validDate;
	private String cardHolder;
	private IdType idType;
	private String idCardnum;
	private String mobile;
	private Currency currency;
	private AcctType acctType;
	private AcctAttr acctAttr;
	private TradeType tradeType;
	private String tradeDesc;
	private String pageNotifyUrl;
	private String notifyUrl;
	private IsSaveBind isBind;
	private String orderDesc;
	private String orderRemarkDesc;
	private IsFixBindInfo isFixBindInfo;
	private GoodsType mxGoodsType;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBindSNo() {
		return bindSNo;
	}

	public void setBindSNo(String bindSNo) {
		this.bindSNo = bindSNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getOrderPName() {
		return orderPName;
	}

	public void setOrderPName(String orderPName) {
		this.orderPName = orderPName;
	}

	public String getOrderPDesc() {
		return orderPDesc;
	}

	public void setOrderPDesc(String orderPDesc) {
		this.orderPDesc = orderPDesc;
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

	public String getIdCardnum() {
		return idCardnum;
	}

	public void setIdCardnum(String idCardnum) {
		this.idCardnum = idCardnum;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public AcctType getAcctType() {
		return acctType;
	}

	public void setAcctType(AcctType acctType) {
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

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getPageNotifyUrl() {
		return pageNotifyUrl;
	}

	public void setPageNotifyUrl(String pageNotifyUrl) {
		this.pageNotifyUrl = pageNotifyUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public IsSaveBind getIsBind() {
		return isBind;
	}

	public void setIsBind(IsSaveBind isBind) {
		this.isBind = isBind;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getOrderRemarkDesc() {
		return orderRemarkDesc;
	}

	public void setOrderRemarkDesc(String orderRemarkDesc) {
		this.orderRemarkDesc = orderRemarkDesc;
	}

	public IsFixBindInfo getIsFixBindInfo() {
		return isFixBindInfo;
	}

	public void setIsFixBindInfo(IsFixBindInfo isFixBindInfo) {
		this.isFixBindInfo = isFixBindInfo;
	}

	public GoodsType getMxGoodsType() {
		return mxGoodsType;
	}

	public void setMxGoodsType(GoodsType mxGoodsType) {
		this.mxGoodsType = mxGoodsType;
	}

}
