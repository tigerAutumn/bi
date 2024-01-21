package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Bank_UnbindCheck extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7841693083825558354L;
	
	//校验结果
	private String checkResult;
	//用户姓名
	private String userName;
	//用户身份证号
	private String idCard;
	
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
}
