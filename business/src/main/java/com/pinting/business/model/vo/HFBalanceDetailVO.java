package com.pinting.business.model.vo;

import java.io.Serializable;

public class HFBalanceDetailVO implements  Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6004613775636255616L;

	private String balance;
	
	private String frozen_amount;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFrozen_amount() {
		return frozen_amount;
	}

	public void setFrozen_amount(String frozen_amount) {
		this.frozen_amount = frozen_amount;
	}
	
	

}
