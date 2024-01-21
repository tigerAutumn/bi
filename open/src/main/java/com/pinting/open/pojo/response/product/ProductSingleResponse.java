package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

public class ProductSingleResponse extends Response {

	private Product product;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
