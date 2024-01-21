package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询某次活动中奖列表
 * @author bianyatian
 * @2017-1-10 下午7:31:22
 */
public class ResMsg_ActivityLuckyDraw_LuckyUserList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4761486995150466102L;
	/*中奖列表*/
	private ArrayList<HashMap<String, Object>> luckyList; 
	
	public ArrayList<HashMap<String, Object>> getLuckyList() {
		return luckyList;
	}

	public void setLuckyList(ArrayList<HashMap<String, Object>> luckyList) {
		this.luckyList = luckyList;
	}

}
