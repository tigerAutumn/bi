package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2915071933391758218L;
	
	private String autoReplyMessage;

	public String getAutoReplyMessage() {
		return autoReplyMessage;
	}

	public void setAutoReplyMessage(String autoReplyMessage) {
		this.autoReplyMessage = autoReplyMessage;
	}
	
	
}
