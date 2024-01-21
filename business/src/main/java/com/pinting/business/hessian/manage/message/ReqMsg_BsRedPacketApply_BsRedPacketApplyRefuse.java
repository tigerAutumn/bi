package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsRedPacketApply_BsRedPacketApplyRefuse extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1760673425023423864L;
	
    private Integer id;
	
	private Integer checker;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getChecker() {
		return checker;
	}

	public void setChecker(Integer checker) {
		this.checker = checker;
	}
	
}
