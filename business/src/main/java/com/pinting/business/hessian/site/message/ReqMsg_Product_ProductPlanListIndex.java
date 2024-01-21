package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Product_ProductPlanListIndex extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -169130439246895586L;
	
    private String userType;   //用户类型
    
    private String terminal;    // 显示端口

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

    
    
}
