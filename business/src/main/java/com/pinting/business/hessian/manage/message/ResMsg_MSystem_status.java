package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MSystem_status extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 916006837527314986L;
	private Integer statusValue;
	private Integer mUserId;
	private String statusKey;
	private String note;
	private Date updateTime;
	
	private ArrayList<HashMap<String, Object>> bsValueList;

	private Integer totalRows;
	
	public Integer getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}

	public Integer getmUserId() {
		return mUserId;
	}

	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}

	public String getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(String statusKey) {
		this.statusKey = statusKey;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public ArrayList<HashMap<String, Object>> getBsValueList() {
		return bsValueList;
	}

	public void setBsValueList(ArrayList<HashMap<String, Object>> bsValueList) {
		this.bsValueList = bsValueList;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	
}
