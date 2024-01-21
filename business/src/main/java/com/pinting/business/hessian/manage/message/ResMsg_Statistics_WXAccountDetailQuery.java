package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_WXAccountDetailQuery extends ResMsg {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 7052984307403072557L;
	private List<HashMap<String, Object>> data;

	public List<HashMap<String, Object>> getData() {
		return data;
	}

	public void setData(List<HashMap<String, Object>> data) {
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