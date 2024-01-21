package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Product_FindRate extends ResMsg {

	private static final long serialVersionUID = 4857153985041190731L;

	private String minRate;
	
	private String maxRate;

	public String getMinRate() {
		return minRate;
	}

	public void setMinRate(String minRate) {
		this.minRate = minRate;
	}

	public String getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(String maxRate) {
		this.maxRate = maxRate;
	}

}
