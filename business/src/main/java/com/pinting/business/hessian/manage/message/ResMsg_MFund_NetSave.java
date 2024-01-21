package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MFund_NetSave extends ResMsg {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4573999728353333909L;


	private Integer id;
	
	private Integer fundId;
	
	private String scale;
	private Date date;
	private Double net;
	private String note;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getFundId() {
		return fundId;
	}
	public void setFundId(Integer fundId) {
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
