package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.model.vo.TrebleGiftListVO;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ActivityLuckyDraw_GetTrebleGiftInvestList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2810245782182416948L;
	
	/*用户当日年化投资金额*/
	private Double buyAmount;
	
	/*三重奖-二重-当日投资排行列表*/
	private ArrayList<HashMap<String, Object>> todayList;
	
	/*三重奖-二重-历史投资排行列表*/
	private ArrayList<HashMap<String, Object>> historyList;

	public ArrayList<HashMap<String, Object>> getTodayList() {
		return todayList;
	}

	public void setTodayList(ArrayList<HashMap<String, Object>> todayList) {
		this.todayList = todayList;
	}

	public Double getBuyAmount() {
		return buyAmount;
	}

	public void setBuyAmount(Double buyAmount) {
		this.buyAmount = buyAmount;
	}

	public ArrayList<HashMap<String, Object>> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(ArrayList<HashMap<String, Object>> historyList) {
		this.historyList = historyList;
	}
	
	
}
