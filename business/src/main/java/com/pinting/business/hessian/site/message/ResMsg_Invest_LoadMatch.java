package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Invest_LoadMatch extends ResMsg {

	private static final long serialVersionUID = -6364372299268149679L;

	private List<HashMap<String,Object>> dataList;
	
	private int total;  //总页数
	
	private String propertySymbol;//资金接收方标记

	public List<HashMap<String, Object>> getDataList() {
		return dataList;
	}

	public void setDataList(List<HashMap<String, Object>> dataList) {
		this.dataList = dataList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
	
}
