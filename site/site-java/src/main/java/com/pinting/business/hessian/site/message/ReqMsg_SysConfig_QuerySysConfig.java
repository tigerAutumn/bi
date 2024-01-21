package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_SysConfig_QuerySysConfig extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 335699740995356653L;
	
	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	

}
