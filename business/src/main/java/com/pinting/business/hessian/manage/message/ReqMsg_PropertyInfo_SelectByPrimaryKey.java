package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_PropertyInfo_SelectByPrimaryKey extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7503349532477419279L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
