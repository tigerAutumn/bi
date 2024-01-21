package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ActivityLuckyDraw_LuckyLenders extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 99435425222620559L;

	/*中奖列表*/
	private ArrayList<HashMap<String, Object>> luckyList; 
	
	public ArrayList<HashMap<String, Object>> getLuckyList() {
		return luckyList;
	}

	public void setLuckyList(ArrayList<HashMap<String, Object>> luckyList) {
		this.luckyList = luckyList;
	}
}
