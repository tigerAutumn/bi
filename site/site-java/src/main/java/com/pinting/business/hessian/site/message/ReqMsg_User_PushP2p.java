package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_PushP2p extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5687162417581304388L;
	
	private String            pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

}
