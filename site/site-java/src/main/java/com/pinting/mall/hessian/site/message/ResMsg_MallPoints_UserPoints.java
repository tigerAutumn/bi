package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallPoints_UserPoints extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1107003657043600494L;

	private long points; //用户总积分
	
	public long getPoints() {
		return points;
	}
	public void setPoints(long points) {
		this.points = points;
	}
}
