package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

public class ResMsg_RegularBuy_EBankBuy extends ResMsg {

	private static final long serialVersionUID = -5454969792251170494L;

	private String orderNo;
	
	private Date orderDate;

	private String resHtml;

	public String getResHtml() {
		return resHtml;
	}

	public void setResHtml(String resHtml) {
		this.resHtml = resHtml;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
}
