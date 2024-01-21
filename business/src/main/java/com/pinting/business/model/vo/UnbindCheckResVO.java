package com.pinting.business.model.vo;

/**
 * 解绑前校验service返回
 * @project business
 * @author bianyatian
 * @2018-5-23 下午5:08:44
 */
public class UnbindCheckResVO {
	
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
