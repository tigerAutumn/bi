package com.pinting.open.pojo.response.product;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Bank;

public class BindBankResponse extends Response {

	private boolean isBindBank;
	
	private Integer subAccountId;
	
	private Double availableBalance;
	
	private List<Bank> dataList;

	public boolean isBindBank() {
		return isBindBank;
	}

	public void setBindBank(boolean isBindBank) {
		this.isBindBank = isBindBank;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public List<Bank> getDataList() {
		return dataList;
	}

	public void setDataList(List<Bank> dataList) {
		this.dataList = dataList;
	}
}
