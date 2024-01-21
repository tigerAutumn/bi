package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Banner_BannerCount extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3463465997402665644L;

	private int count;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
