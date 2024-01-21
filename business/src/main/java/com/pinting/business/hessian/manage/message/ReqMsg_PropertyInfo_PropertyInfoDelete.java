package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_PropertyInfo_PropertyInfoDelete extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5938498454540734287L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
