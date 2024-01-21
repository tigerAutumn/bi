package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午2:33:11
 */
public class ReqMsg_OperationalWxUser_isExist extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8395580233273830686L;
	
	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
