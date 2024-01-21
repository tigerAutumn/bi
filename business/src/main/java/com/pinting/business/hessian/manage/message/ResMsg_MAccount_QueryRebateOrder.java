package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAccount_QueryRebateOrder extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2015968471902862149L;
	
	private ArrayList<HashMap<String,Object>> valueList;
	private Integer totalRows;
	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}
	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	
}
