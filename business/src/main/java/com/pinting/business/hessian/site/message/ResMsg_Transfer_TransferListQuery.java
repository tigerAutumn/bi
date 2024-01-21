package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Transfer_TransferListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5332936567979981660L;

	
	private ArrayList<HashMap<String,Object>> valueList;

	private int total;  //总页数
	
	private int pageIndex; //当前页码
	
	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
}
