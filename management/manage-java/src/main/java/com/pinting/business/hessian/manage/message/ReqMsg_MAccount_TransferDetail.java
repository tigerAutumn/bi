package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_TransferDetail extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7706591670621240005L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
