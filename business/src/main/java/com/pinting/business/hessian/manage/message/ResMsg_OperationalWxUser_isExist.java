package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午4:50:15
 */
public class ResMsg_OperationalWxUser_isExist extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4183454308636892181L;
	
	private Integer WxUserId;
	
	public Integer getWxUserId() {
		return WxUserId;
	}
	public void setWxUserId(Integer wxUserId) {
		WxUserId = wxUserId;
	}
	
}
