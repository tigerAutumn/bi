package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

public class CardBinResponse extends Response {

	private Integer bankId;
	
    private String	bankCardFuncType;

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getBankCardFuncType() {
		return bankCardFuncType;
	}

	public void setBankCardFuncType(String bankCardFuncType) {
		this.bankCardFuncType = bankCardFuncType;
	}
}
