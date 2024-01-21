package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7829932587440405748L;
	
	private Integer id;
	
	private Integer checker;

	public Integer getChecker() {
		return checker;
	}

	public void setChecker(Integer checker) {
		this.checker = checker;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
