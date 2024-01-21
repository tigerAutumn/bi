package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallSign_UserSign extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7480078754381355321L;

	private String isSign; //签到标识-YES表示已签到
	
	private String signSucc; //此次签到是否成功，-YES表示成功
	
	private long signPoints; //签到获得积分
	
	private Integer signDays; //已签到天数

	public String getIsSign() {
		return isSign;
	}

	public void setIsSign(String isSign) {
		this.isSign = isSign;
	}

	public String getSignSucc() {
		return signSucc;
	}

	public void setSignSucc(String signSucc) {
		this.signSucc = signSucc;
	}

	public long getSignPoints() {
		return signPoints;
	}

	public void setSignPoints(long signPoints) {
		this.signPoints = signPoints;
	}

	public Integer getSignDays() {
		return signDays;
	}

	public void setSignDays(Integer signDays) {
		this.signDays = signDays;
	}
	
	
}
