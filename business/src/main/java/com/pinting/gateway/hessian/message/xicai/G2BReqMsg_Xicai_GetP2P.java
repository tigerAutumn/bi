package com.pinting.gateway.hessian.message.xicai;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Xicai_GetP2P extends ReqMsg {

	private static final long serialVersionUID = -6006687749228436397L;

	private Integer pid;

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
}
