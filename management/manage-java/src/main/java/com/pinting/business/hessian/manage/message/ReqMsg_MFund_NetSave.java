package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MFund_NetSave extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3973489333009727920L;


	private Integer id;
	
	private Integer fundId;
	
	private String scale;
	private Date date;
	private Double net;
	private String note;
	private String operateType;
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
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
