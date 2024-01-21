package com.pinting.business.model.vo;

import java.util.Date;

public class PayNoticeVO {
	private String orderNo;
	private String status;
	private String mpOrderNo; 
	private Date orderDate;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMpOrderNo() {
		return mpOrderNo;
	}
	public void setMpOrderNo(String mpOrderNo) {
		this.mpOrderNo = mpOrderNo;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
