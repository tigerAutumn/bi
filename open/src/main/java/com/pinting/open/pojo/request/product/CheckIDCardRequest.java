package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

public class CheckIDCardRequest extends Request {

	private String IDCard;
	
	public String getIDCard() {
		return IDCard;
	}

	public void setIDCard(String iDCard) {
		IDCard = iDCard;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/checkIDCard";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/checkIDCard";
	}

}
