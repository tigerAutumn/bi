package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Banner_BannerCount extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7604101405538167937L;

	private String channel;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
