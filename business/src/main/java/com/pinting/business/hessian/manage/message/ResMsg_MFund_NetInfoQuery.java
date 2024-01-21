package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MFund_NetInfoQuery extends ResMsg {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -702614041121363514L;

	private int id;
	
	private int fundId;
	
	private String scale;
	private Date date;
	private Double net;
	private String note;
private ArrayList<HashMap<String,Object>> FundList;
	
	

	public ArrayList<HashMap<String, Object>> getFundList() {
		return FundList;
	}

	public void setFundList(ArrayList<HashMap<String, Object>> fundList) {
		FundList = fundList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFundId() {
		return fundId;
	}
	public void setFundId(int fundId) {
		this.fundId = fundId;
	}
	public String getScale() {
		return scale;
	}
	public void setScale(String scale) {
		this.scale = scale;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Double getNet() {
		return net;
	}
	public void setNet(Double net) {
		this.net = net;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}

	
}
