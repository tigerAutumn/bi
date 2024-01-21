package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_SysMessage_User178Message extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3199191440664398527L;
	private List<Map<String, Object>> sysMessages;
	public List<Map<String, Object>> getSysMessages() {
		return sysMessages;
	}
	public void setSysMessages(List<Map<String, Object>> sysMessages) {
		this.sysMessages = sysMessages;
	}

	
	
}
