package com.pinting.gateway.dafy.out.model;

import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.Data;


public class QueryWXAccountDetailResModel extends BaseResModel {
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
