package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsRedPacketApply;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AutoPacket_Init extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5223958659928197322L;
	private ArrayList<BsAgent> agentList; //渠道选择
	private ArrayList<BsRedPacketApply> packetList; //可选红包类型
	
	public ArrayList<BsAgent> getAgentList() {
		return agentList;
	}
	public void setAgentList(ArrayList<BsAgent> agentList) {
		this.agentList = agentList;
	}
	public ArrayList<BsRedPacketApply> getPacketList() {
		return packetList;
	}
	public void setPacketList(ArrayList<BsRedPacketApply> packetList) {
		this.packetList = packetList;
	}
	
}
