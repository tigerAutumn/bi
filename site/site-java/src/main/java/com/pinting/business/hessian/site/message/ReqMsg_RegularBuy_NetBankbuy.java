package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_NetBankbuy extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -836644356788999007L;
	
	private Date orderDate;
	private String orderId;
	private String mxUserIp;
	private String orderPName;
	private String orderPDesc;
	private String userMobile;
	private String orderDesc;
	private String tradeDesc;
	private Integer userId;
	private Integer productId;
	private double money;
	private String bankId;//银行编号
	private String flag;

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
	
	public double getMoney() {
		return money;
	}
	public void setMoney(double money) {
		this.money = money;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}
