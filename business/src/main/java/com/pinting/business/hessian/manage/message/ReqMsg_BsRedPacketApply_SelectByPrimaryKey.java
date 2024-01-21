package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsRedPacketApply_SelectByPrimaryKey extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -316482440580767487L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
