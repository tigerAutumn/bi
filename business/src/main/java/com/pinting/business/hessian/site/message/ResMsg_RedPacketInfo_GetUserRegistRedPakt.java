package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RedPacketInfo_GetUserRegistRedPakt extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1328198947072429105L;
	
	List<Map<String, Object>> userRegistRedPakts;

	public List<Map<String, Object>> getUserRegistRedPakts() {
		return userRegistRedPakts;
	}

	public void setUserRegistRedPakts(List<Map<String, Object>> userRegistRedPakts) {
		this.userRegistRedPakts = userRegistRedPakts;
	}
	

}
