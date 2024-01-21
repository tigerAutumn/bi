package com.pinting.business.model.vo;

import com.pinting.business.model.BsSmsPlatformsConfig;

public class BsSmsPlatformsConfigVO extends BsSmsPlatformsConfig {

	private double rate;//60分钟内的成功率

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}
}
