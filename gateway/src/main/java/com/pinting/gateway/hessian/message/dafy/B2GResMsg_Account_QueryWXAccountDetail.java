package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.dafy.model.Data;

public class B2GResMsg_Account_QueryWXAccountDetail extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7762182954706740433L;

	private List<Data> data;

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}
	private double balance;
	private String count;
	private String currPage;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCurrPage() {
		return currPage;
	}

	public void setCurrPage(String currPage) {
		this.currPage = currPage;
	}
	
}
