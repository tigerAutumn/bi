package com.pinting.business.hessian.manage.message;


import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUser_BsUserQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 912519799216038313L;
	private Integer userId;
	private Integer status;
	private String nick;
	private String userName;
	private String mobile;
	private String email;
	private String idCard;
	private Integer isBindBank;
	
	public Integer getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	/**
	 * 以下循环：
	 * id						编号
	 * cardNo				卡号
	 * cardNoSim		简单卡号
	 * cardOwner		所有者
 	 * bankId 				银行编号
 	 * bankName 				银行编号
	 * status				状态
	 * bindTime			绑定时间
	 */
	private List<HashMap<String, Object>> userBankCardList;
	public List<HashMap<String, Object>> getUserBankCardList() {
		return userBankCardList;
	}
	public void setUserBankCardList(List<HashMap<String, Object>> userBankCardList) {
		this.userBankCardList = userBankCardList;
	}


	
}
