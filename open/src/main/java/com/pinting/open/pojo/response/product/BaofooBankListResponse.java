package com.pinting.open.pojo.response.product;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Bank;

public class BaofooBankListResponse extends Response {
	private List<Bank> dataList;

	public List<Bank> getDataList() {
		return dataList;
	}

	public void setDataList(List<Bank> dataList) {
		this.dataList = dataList;
	}
}
