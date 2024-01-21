package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RegularBuy_Order extends ResMsg {

	private static final long serialVersionUID = -330843117364224418L;

	private Integer userId;
	
	private String orderNo; //币港湾订单号
	
	private String mpOrderNo; //19付订单号
	
	private Date orderDate;
	
	private String html; //调用融宝卡密鉴权接口的html代码
	
	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
