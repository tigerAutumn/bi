package com.pinting.open.pojo.response.product;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

public class ProductListStatusFinish30Response extends Response {
	private List<Product> productList; //产品列表
	
	private Integer count; //总页数

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
