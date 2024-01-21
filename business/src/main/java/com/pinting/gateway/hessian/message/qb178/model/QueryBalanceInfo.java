package com.pinting.gateway.hessian.message.qb178.model;

import java.io.Serializable;

public class QueryBalanceInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7061833110155457403L;
	/* 会员账户 */
	private String user_account;
	/* 总余额（金额单位：分） */
	private Long total_balance;
	/* 可用余额（金额单位：分） */
	private Long current_balance;
	/* 冻结金额（金额单位：分）*/
	private Long frozen_balance;
	
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public Long getTotal_balance() {
		return total_balance;
	}
	public void setTotal_balance(Long total_balance) {
		this.total_balance = total_balance;
	}
	public Long getCurrent_balance() {
		return current_balance;
	}
	public void setCurrent_balance(Long current_balance) {
		this.current_balance = current_balance;
	}
	public Long getFrozen_balance() {
		return frozen_balance;
	}
	public void setFrozen_balance(Long frozen_balance) {
		this.frozen_balance = frozen_balance;
	}

	
}
