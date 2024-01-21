package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_PlayerKilling_ActivityStatus extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8464072914822756199L;
	/*知足派标的状态*/
	private		String		statusContentment;
	/*现实派标的状态*/
	private		String		statusReal;
	public String getStatusContentment() {
		return statusContentment;
	}
	public void setStatusContentment(String statusContentment) {
		this.statusContentment = statusContentment;
	}
	public String getStatusReal() {
		return statusReal;
	}
	public void setStatusReal(String statusReal) {
		this.statusReal = statusReal;
	}
	
}
