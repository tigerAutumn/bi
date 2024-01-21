package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_WxAutoReply_SelectAutoReplyMessage extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 74964484938488856L;
	
	private ArrayList<HashMap<String, Object>> dataGrid;

	public ArrayList<HashMap<String, Object>> getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
		this.dataGrid = dataGrid;
	}
	
	

}
