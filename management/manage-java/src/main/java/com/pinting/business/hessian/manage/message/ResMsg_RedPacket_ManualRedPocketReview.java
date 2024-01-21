package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RedPacket_ManualRedPocketReview extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2969885190332692007L;
	
	private ArrayList<HashMap<String, Object>> dataGrid;
	
	private HashMap<String, Object> manualPocketMap ;
	
	private ArrayList<HashMap<String, Object>> queryHistoryList ;
    
    private Integer count;
    
	private List<String> notifyList ;

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

	public HashMap<String, Object> getManualPocketMap() {
		return manualPocketMap;
	}

	public void setManualPocketMap(HashMap<String, Object> manualPocketMap) {
		this.manualPocketMap = manualPocketMap;
	}

	public List<String> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<String> notifyList) {
		this.notifyList = notifyList;
	}

	public ArrayList<HashMap<String, Object>> getQueryHistoryList() {
		return queryHistoryList;
	}

	public void setQueryHistoryList(
			ArrayList<HashMap<String, Object>> queryHistoryList) {
		this.queryHistoryList = queryHistoryList;
	}

	
}
