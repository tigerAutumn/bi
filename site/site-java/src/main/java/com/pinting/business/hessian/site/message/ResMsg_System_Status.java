package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_System_Status extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3123210268680310316L;

	private Integer sysValue;
	private Integer tranValue;
	public Integer getSysValue() {
		return sysValue;
	}
	public void setSysValue(Integer sysValue) {
		this.sysValue = sysValue;
	}
	public Integer getTranValue() {
		return tranValue;
	}
	public void setTranValue(Integer tranValue) {
		this.tranValue = tranValue;
	}
	
		
}
