package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_ProductListReturnType extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1709166666714850651L;
	
	private List<Map<String,Object>> productList;

	public List<Map<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(List<Map<String, Object>> productList) {
		this.productList = productList;
	}
	
	
}
