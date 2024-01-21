package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;

import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AutoPacket_GetPolicyType extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2875790212550052492L;
	
	private ArrayList<BsRedPacketCheck> checkList;

	public ArrayList<BsRedPacketCheck> getCheckList() {
		return checkList;
	}

	public void setCheckList(ArrayList<BsRedPacketCheck> checkList) {
		this.checkList = checkList;
	}

}
