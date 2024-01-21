package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsSales_StatusModify extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350704143652272155L;
	
	private Integer id;
	
	private Integer status;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
