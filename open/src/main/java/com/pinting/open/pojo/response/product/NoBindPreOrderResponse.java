package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class NoBindPreOrderResponse extends Response {

	private String orderNo;
	
	private String html;

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
