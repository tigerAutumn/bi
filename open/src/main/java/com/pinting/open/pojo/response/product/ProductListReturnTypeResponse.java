package com.pinting.open.pojo.response.product;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

public class ProductListReturnTypeResponse extends Response {
	private List<Product> productList;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
	
}
