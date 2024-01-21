package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询618活动主页出参
 * @author bianyatian
 * @2016-6-6 下午3:27:24
 */
public class ResMsg_ActivityLuckyDraw_Activity618Index extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3446627819755295761L;

	/*中奖列表*/
	private ArrayList<HashMap<String, Object>> luckyList; 
	/*当前已抽奖人数*/
	private Integer luckyNum;
	/*用户可抽奖次数*/
	private Integer userLuckyNum;
	
	public ArrayList<HashMap<String, Object>> getLuckyList() {
		return luckyList;
	}

	public void setLuckyList(ArrayList<HashMap<String, Object>> luckyList) {
		this.luckyList = luckyList;
	}

	public Integer getLuckyNum() {
		return luckyNum;
	}

	public void setLuckyNum(Integer luckyNum) {
		this.luckyNum = luckyNum;
	}

	public Integer getUserLuckyNum() {
		return userLuckyNum;
	}

	public void setUserLuckyNum(Integer userLuckyNum) {
		this.userLuckyNum = userLuckyNum;
	}

}
