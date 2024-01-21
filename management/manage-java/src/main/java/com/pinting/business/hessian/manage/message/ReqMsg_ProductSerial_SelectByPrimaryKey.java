package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductSerial_SelectByPrimaryKey extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3922009203211756654L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
