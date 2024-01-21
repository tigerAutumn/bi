package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class BankCardUnbindCheckResponse extends Response {

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
