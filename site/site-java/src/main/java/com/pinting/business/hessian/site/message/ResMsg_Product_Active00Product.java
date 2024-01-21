package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_Active00Product extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6983495523763511877L;
	
	private String isEndOrNoStart; // 活动已结束或未开始   isEnd...noStart  
	
	private ArrayList<HashMap<String,Object>> productList;// 产品列表

	public ArrayList<HashMap<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(ArrayList<HashMap<String, Object>> productList) {
		this.productList = productList;
	}
	

	public String getIsEndOrNoStart() {
		return isEndOrNoStart;
	}

	public void setIsEndOrNoStart(String isEndOrNoStart) {
		this.isEndOrNoStart = isEndOrNoStart;
	}
}
