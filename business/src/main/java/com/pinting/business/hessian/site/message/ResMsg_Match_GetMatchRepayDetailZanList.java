package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Match_GetMatchRepayDetailZanList extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8848722956513687321L;

	/*债权关系还款明细列表*/
	private ArrayList<HashMap<String, Object>> list;

	public ArrayList<HashMap<String, Object>> getList() {
		return list;
	}

	public void setList(ArrayList<HashMap<String, Object>> list) {
		this.list = list;
	}
	
	
}
