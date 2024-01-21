package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午5:38:09
 */
public class ResMsg_OperationalWxUser_Regist extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2451625257629295045L;

	private Integer bsActivityUser;
	public Integer getBsActivityUser() {
		return bsActivityUser;
	}
	public void setBsActivityUser(Integer bsActivityUser) {
		this.bsActivityUser = bsActivityUser;
	}
}
