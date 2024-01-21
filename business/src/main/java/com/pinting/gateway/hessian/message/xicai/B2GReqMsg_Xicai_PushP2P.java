package com.pinting.gateway.hessian.message.xicai;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Xicai_PushP2P extends ReqMsg{

	private static final long serialVersionUID = 2275322378277975259L;

	private String pid;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}
