package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_BankListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383506657546620748L;
	/**
	 * 以下循环：
	 * 	cardOwner	持卡人姓名	必填	String
	 *	bankId		银行编号	必填	Integer
	 *	bankName	银行名称	必填	String
	 *	userId		用户编号	必填	Integer
	 *	cardNo		卡号		必填	Integer
	 */
	private ArrayList<HashMap<String, Object>>  bankCards;
	private String userName;
	private String idCard;
	private int isBindName;
	
	private int isBindBank;
	
	private Integer isBinding; //是否是正在绑定，如果是正在绑定中则为1，否则为0
	
	
	
	
	public Integer getIsBinding() {
		return isBinding;
	}
	public void setIsBinding(Integer isBinding) {
		this.isBinding = isBinding;
	}
	public int getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(int isBindBank) {
		this.isBindBank = isBindBank;
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
	public int getIsBindName() {
		return isBindName;
	}
	public void setIsBindName(int isBindName) {
		this.isBindName = isBindName;
	}
	public ArrayList<HashMap<String, Object>> getBankCards() {
		return bankCards;
	}
	public void setBankCards(ArrayList<HashMap<String, Object>> bankCards) {
		this.bankCards = bankCards;
	}

}
