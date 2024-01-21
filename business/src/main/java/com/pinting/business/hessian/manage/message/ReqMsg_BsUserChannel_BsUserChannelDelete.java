package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUserChannel_BsUserChannelDelete extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6052825367677959598L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
