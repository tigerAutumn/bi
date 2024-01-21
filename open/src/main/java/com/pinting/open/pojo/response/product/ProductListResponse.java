package com.pinting.open.pojo.response.product;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

public class ProductListResponse extends Response {

	private List<Product> dataList;
	
	private Integer count;

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Product> getDataList() {
		return dataList;
	}

	public void setDataList(List<Product> dataList) {
		this.dataList = dataList;
	}
}
