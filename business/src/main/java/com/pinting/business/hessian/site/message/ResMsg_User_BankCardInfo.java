package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2017年11月22日 下午5:27:58
 */
public class ResMsg_User_BankCardInfo extends ResMsg {

	private static final long serialVersionUID = 7344704481059146750L;
	
	private String userName;
	
	private String mobile;
	
	private String idCard;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
}
