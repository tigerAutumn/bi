package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BannerConfig_GetBannerCount extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7383876227114315319L;

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
