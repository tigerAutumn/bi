 package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 预绑卡入参
 * @author Dragon & cat
 * @date 2016-8-28
 */
public class ReqMsg_Bank_PreBindCard extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -469295887687275921L;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 银行卡号
	 */
	private String cardNo; 
	/**
	 * 手机号
	 */
	private String mobile; 
	/**
	 * 银行ID
	 */
	private String bankId;
	/**
	 * 用户ID
	 */
	private String userId; 
	/**
	 * 端口号
	 */
	private String terminalType;
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBankId() {
		return bankId;
	}
	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTerminalType() {
		return terminalType;
	}
	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}
	
}
