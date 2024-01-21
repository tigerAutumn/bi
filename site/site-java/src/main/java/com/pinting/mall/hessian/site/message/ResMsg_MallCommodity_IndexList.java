package com.pinting.mall.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallCommodity_IndexList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5772416128766525500L;
	
	/*商品列表*/
	private ArrayList<HashMap<String, Object>> commodityList;

	public ArrayList<HashMap<String, Object>> getCommodityList() {
		return commodityList;
	}

	public void setCommodityList(ArrayList<HashMap<String, Object>> commodityList) {
		this.commodityList = commodityList;
	}

}
