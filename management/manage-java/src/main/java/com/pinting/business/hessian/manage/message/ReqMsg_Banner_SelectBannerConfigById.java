package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Banner_SelectBannerConfigById extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4619697289510256053L;
	
	private Integer bannerId;

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}
	

}
