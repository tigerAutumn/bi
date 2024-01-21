package com.pinting.mall.model.vo;

import java.util.Date;

/**
 * 管理台订单管理VO
 * @author SHENGUOPING
 * @date  2018年5月14日 下午5:23:56
 */
public class MallExchangeOrdersVO extends PageInfoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4530273688615726181L;
	
	// 序号
	private Integer serialNo;
	
	// 商品名称
	private String commName;
	
	// 商品属性： 虚：EMPTY，实：REAL
	private String commProperty;
	
	// 手机号
	private String mobile;
	
	// 兑换开始时间 
	private String exchangeStartTime;
	
	// 兑换结束时间
	private String exchangeEndTime;
	
	// 收货人姓名
	private String recName;
	
	// 收货人手机号
	private String recMobile;
	
	// 收货人地址
	private String recAdress;
	
	// 收货人详细地址
	private String recAdressDetail;
	
	// 状态
	private String status;
	
	// 订单id
	private Integer orderId;
	
	private Date exchangeTime;
	
	private String commTypeName;

	private Integer payPoints; 
	
	// 发货时间
	private Date deliveryTime;
	
	// 收货信息
	private String deliveryInfo;
	
	// 商品类别code
	private String commTypeCode;
	
	// 订单状态
	private String orderStatus;
	
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public String getCommProperty() {
		return commProperty;
	}

	public void setCommProperty(String commProperty) {
		this.commProperty = commProperty;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getExchangeStartTime() {
		return exchangeStartTime;
	}

	public void setExchangeStartTime(String exchangeStartTime) {
		this.exchangeStartTime = exchangeStartTime;
	}

	public String getExchangeEndTime() {
		return exchangeEndTime;
	}

	public void setExchangeEndTime(String exchangeEndTime) {
		this.exchangeEndTime = exchangeEndTime;
	}

	public String getRecName() {
		return recName;
	}

	public void setRecName(String recName) {
		this.recName = recName;
	}

	public String getRecMobile() {
		return recMobile;
	}

	public void setRecMobile(String recMobile) {
		this.recMobile = recMobile;
	}

	public String getRecAdress() {
		return recAdress;
	}

	public void setRecAdress(String recAdress) {
		this.recAdress = recAdress;
	}

	public String getRecAdressDetail() {
		return recAdressDetail;
	}

	public void setRecAdressDetail(String recAdressDetail) {
		this.recAdressDetail = recAdressDetail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public String getCommTypeName() {
		return commTypeName;
	}

	public void setCommTypeName(String commTypeName) {
		this.commTypeName = commTypeName;
	}

	public Integer getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(Integer payPoints) {
		this.payPoints = payPoints;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public String getCommTypeCode() {
		return commTypeCode;
	}

	public void setCommTypeCode(String commTypeCode) {
		this.commTypeCode = commTypeCode;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	
}
