package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.Currency;
import com.pinting.gateway.hessian.message.pay19.enums.TradeType;

public class B2GReqMsg_NetBank_EBank extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3919210458444571167L;
	private Date orderDate;
	private String orderId;
	private double amount;
	/*private Currency currency;*/
	private String mxUserIp;
	/*private String retUrl;
	private String notifyUrl;*/
	private String orderPName;
	private String orderPDesc;
	private String userMobile;
	/*private String mxHomePage;*/
	/*private String innerFlag = "OUTER";*/
	private String orderDesc;
	/*private TradeType tradeType;*/
	private String tradeDesc;
	private String flag;
	private String bankId;
	
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMxUserIp() {
		return mxUserIp;
	}
	public void setMxUserIp(String mxUserIp) {
		this.mxUserIp = mxUserIp;
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
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	public String getTradeDesc() {
		return tradeDesc;
	}
	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
}
