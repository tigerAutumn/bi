package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductSerial_ProductSerialDelete extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6332532171359737393L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
