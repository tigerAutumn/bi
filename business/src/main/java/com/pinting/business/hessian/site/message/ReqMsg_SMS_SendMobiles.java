package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_SMS_SendMobiles extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 880369528515756521L;
	//多个手机号，以逗号隔开；如果手机号获得自后台，则可以为空，否则必输
	private String mobiles;
	@NotNull(message="发送信息不能为空")
	private String message;
	//手机号类型：normal-普通类型，此时mobiles字段必输；emergency-内部告警手机号，此时mobiles为空
	@NotNull(message="手机号类型不能为空")
	private String mobileType;
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMobileType() {
		return mobileType;
	}
	public void setMobileType(String mobileType) {
		this.mobileType = mobileType;
	}
	
}
