package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class SelectProductInformResponse extends Response {

	private boolean isExist;

	public boolean isExist() {
		return isExist;
	}

	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
}
