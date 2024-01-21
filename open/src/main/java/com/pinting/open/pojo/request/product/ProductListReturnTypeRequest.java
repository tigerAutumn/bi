package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class ProductListReturnTypeRequest extends Request {
	
	private  String   returnType; //回款类型
	
	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/productListReturnType";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/productListReturnType";
	}

}
