package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AdEffect_SelectAdEffectInfo extends ResMsg {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3493995781432026509L;

	private ArrayList<HashMap<String, Object>> dataGrid;
	
    private Integer count;

	public ArrayList<HashMap<String, Object>> getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
		this.dataGrid = dataGrid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
    
    
}
