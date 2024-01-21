package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class BalanceBuyResponse extends Response {

	private  String   isAutoRedPocket; //是否发放了自动红包

	public String getIsAutoRedPocket() {
		return isAutoRedPocket;
	}

	public void setIsAutoRedPocket(String isAutoRedPocket) {
		this.isAutoRedPocket = isAutoRedPocket;
	}

}
