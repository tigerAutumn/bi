package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_SaveQuestionnaire extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7319245769092510550L;
	
	/** 风险测评类型  */
	private String assessType;

	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	
}
