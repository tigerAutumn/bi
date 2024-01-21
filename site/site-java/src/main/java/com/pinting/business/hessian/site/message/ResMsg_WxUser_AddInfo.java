package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_WxUser_AddInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7309577061080266377L;

	private Integer dealCount;

	public Integer getDealCount() {
		return dealCount;
	}

	public void setDealCount(Integer dealCount) {
		this.dealCount = dealCount;
	}
}
