package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_AutoPacket_GetPolicyType extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8995237973037135270L;
	
	private String policyType;

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}
}
