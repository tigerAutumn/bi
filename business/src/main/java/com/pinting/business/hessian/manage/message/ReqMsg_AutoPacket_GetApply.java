package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_AutoPacket_GetApply extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6351964045331652018L;
	private String applyNo;
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	

}
