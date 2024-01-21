package com.pinting.gateway.qb178.in.model;

import java.io.Serializable;

public class PositionProduct4UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3904797483759469770L;
	/* 会员账户 */
	private String user_account;
	/* 持仓余额  */
	private Long ta_balance;
	
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public Long getTa_balance() {
		return ta_balance;
	}
	public void setTa_balance(Long ta_balance) {
		this.ta_balance = ta_balance;
	}
	
}
