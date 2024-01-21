package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Game_BonusCheck extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 990611783311789500L;

	private boolean isHelped;

	public boolean isHelped() {
		return isHelped;
	}

	public void setHelped(boolean isHelped) {
		this.isHelped = isHelped;
	}
 
	
}
