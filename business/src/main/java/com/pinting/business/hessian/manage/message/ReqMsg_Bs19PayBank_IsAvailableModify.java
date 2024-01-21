package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Bs19PayBank_IsAvailableModify extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4193883276048032964L;
	
	private Integer id;
	
	private Integer isAvailable;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
}
