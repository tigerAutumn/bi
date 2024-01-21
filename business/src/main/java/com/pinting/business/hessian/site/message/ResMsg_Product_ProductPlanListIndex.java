package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_ProductPlanListIndex extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7559460436205253568L;
	
	private ArrayList<HashMap<String,Object>> productList;

	public ArrayList<HashMap<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<HashMap<String, Object>> productList) {
		this.productList = productList;
	}
	

}
