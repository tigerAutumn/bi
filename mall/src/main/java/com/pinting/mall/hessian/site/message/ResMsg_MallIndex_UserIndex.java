package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallIndex_UserIndex extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8712481135333010261L;

	private String isSign; //签到标识-YES表示已签到
	
	private long points; //用户总积分

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}
	
	
}
