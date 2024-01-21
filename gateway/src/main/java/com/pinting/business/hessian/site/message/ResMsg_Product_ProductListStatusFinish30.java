package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_ProductListStatusFinish30 extends ResMsg {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7536668169928030887L;

	private List<Map<String,Object>> productList;
	
	private Integer count; //总页数

	public List<Map<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(List<Map<String, Object>> productList) {
		this.productList = productList;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
