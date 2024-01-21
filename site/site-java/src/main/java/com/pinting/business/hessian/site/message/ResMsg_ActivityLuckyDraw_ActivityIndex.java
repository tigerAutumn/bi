package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 活动主页返回
 * @author bianyatian
 * @2016-10-29 下午3:53:14
 */
public class ResMsg_ActivityLuckyDraw_ActivityIndex extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1138242846498062874L;
	
	/*中奖列表*/
	private ArrayList<HashMap<String, Object>> luckyList; 
	/*当前已抽奖人数*/
	private Integer luckyNum;
	/*用户可抽奖次数*/
	private Integer userLuckyNum;
	/*活动开始时间 yyyy-MM-dd HH:mm:ss*/
	private String startTime;
	/*活动结束时间 yyyy-MM-dd HH:mm:ss*/
	private String endTime;
	/*是否开始，noStart-未开始，end-已结束，ing进行中*/
	private String isStart;
	
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

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getIsStart() {
		return isStart;
	}

	public void setIsStart(String isStart) {
		this.isStart = isStart;
	}


}
