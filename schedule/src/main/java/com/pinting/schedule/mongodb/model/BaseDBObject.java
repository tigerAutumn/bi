package com.pinting.schedule.mongodb.model;

import java.io.Serializable;


public class BaseDBObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6330259334950898591L;

	private String update_time;
	
	private double total;
	
	private int count;
	
	private String trans_type;
	
	private String partner_code;

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getTrans_type() {
		return trans_type;
	}

	public void setTrans_type(String trans_type) {
		this.trans_type = trans_type;
	}

	public String getPartner_code() {
		return partner_code;
	}

	public void setPartner_code(String partner_code) {
		this.partner_code = partner_code;
	}
}