package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class BindPreOrderResponse extends Response {

	private String orderNo;
	
	private String mobile;
	
	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
