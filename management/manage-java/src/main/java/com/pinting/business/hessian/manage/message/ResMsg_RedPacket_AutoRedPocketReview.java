package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RedPacket_AutoRedPocketReview extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5101687968695253476L;
	
	
	private HashMap<String, Object> autoPocketMap ;
	
	private ArrayList<HashMap<String, Object>> agentList ;
	
	private List<String> notifyList ;

	public HashMap<String, Object> getAutoPocketMap() {
		return autoPocketMap;
	}

	public void setAutoPocketMap(HashMap<String, Object> autoPocketMap) {
		this.autoPocketMap = autoPocketMap;
	}

	public ArrayList<HashMap<String, Object>> getAgentList() {
		return agentList;
	}

	public void setAgentList(ArrayList<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
	}

	public List<String> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<String> notifyList) {
		this.notifyList = notifyList;
	}
	
	
}
