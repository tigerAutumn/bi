package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class ReapalQuickCMBRequest extends Request {

	private String orderNo;
	private Integer productId;
	
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/reapalQuickCMB";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/reapalQuickCMB";
	}




}
