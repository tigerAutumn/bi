package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RedPacket_GetNewAutoRedPacket extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8671219126930455739L;

	private HashMap<String, Object> autoPocketMap ;
	
	private List<String> agentList ;
	
	private List<String> notifyList ;
	
	private List<String> termList ;

	public HashMap<String, Object> getAutoPocketMap() {
		return autoPocketMap;
	}

	public void setAutoPocketMap(HashMap<String, Object> autoPocketMap) {
		this.autoPocketMap = autoPocketMap;
	}

	public List<String> getNotifyList() {
		return notifyList;
	}

	public void setNotifyList(List<String> notifyList) {
		this.notifyList = notifyList;
	}

	public List<String> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<String> agentList) {
		this.agentList = agentList;
	}

	public List<String> getTermList() {
		return termList;
	}

	public void setTermList(List<String> termList) {
		this.termList = termList;
	}

}
