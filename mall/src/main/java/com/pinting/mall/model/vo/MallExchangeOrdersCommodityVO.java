package com.pinting.mall.model.vo;

import java.util.Date;

public class MallExchangeOrdersCommodityVO {
	
	/** 积分商城订单表数据  */
	private Integer orderId; //积分商城订单表id

    private String orderNo;

    private Integer userId;

    private Integer commId;

    private Long payPoints;

    private Integer buyNum;

    private String orderStatus;

    private String orderNote;
    
    private Date orderSuccTime; //订单修改时间
    
    
    /** 商品信息表数据  */
    private String commName; //商品名称

    private Integer commTypeId; //商品类别id
    
    private String commProperty; //商品属性
    
    /** 商品类别表数据 */ 
    
    private String commTypeName; //类别名称

    private String commTypeCode; //类别编码

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getCommId() {
		return commId;
	}

	public void setCommId(Integer commId) {
		this.commId = commId;
	}

	public Long getPayPoints() {
		return payPoints;
	}

	public void setPayPoints(Long payPoints) {
		this.payPoints = payPoints;
	}

	public Integer getBuyNum() {
		return buyNum;
	}

	public void setBuyNum(Integer buyNum) {
		this.buyNum = buyNum;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderNote() {
		return orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getCommName() {
		return commName;
	}

	public void setCommName(String commName) {
		this.commName = commName;
	}

	public Integer getCommTypeId() {
		return commTypeId;
	}

	public void setCommTypeId(Integer commTypeId) {
		this.commTypeId = commTypeId;
	}

	public String getCommProperty() {
		return commProperty;
	}

	public void setCommProperty(String commProperty) {
		this.commProperty = commProperty;
	}

	public String getCommTypeName() {
		return commTypeName;
	}

	public void setCommTypeName(String commTypeName) {
		this.commTypeName = commTypeName;
	}

	public String getCommTypeCode() {
		return commTypeCode;
	}

	public void setCommTypeCode(String commTypeCode) {
		this.commTypeCode = commTypeCode;
	}

	public Date getOrderSuccTime() {
		return orderSuccTime;
	}

	public void setOrderSuccTime(Date orderSuccTime) {
		this.orderSuccTime = orderSuccTime;
	}
    
    

}
