package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.business.model.BsWxAutoReply;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MWxAutoReply_GetReplyById extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5322851619289879110L;

	private BsWxAutoReply wxAutoReply;

	public BsWxAutoReply getWxAutoReply() {
		return wxAutoReply;
	}

	public void setWxAutoReply(BsWxAutoReply wxAutoReply) {
		this.wxAutoReply = wxAutoReply;
	}
    
    

}
