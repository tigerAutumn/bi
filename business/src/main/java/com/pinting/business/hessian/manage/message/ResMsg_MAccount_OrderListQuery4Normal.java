package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAccount_OrderListQuery4Normal extends ResMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7088818135407443103L;

	private ArrayList<HashMap<String,Object>> valueList;
	
	private List<HashMap<String, Object>> buyBankTypeList;
	
	private List<HashMap<String, Object>> agentList;

	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	private Integer status;
	
	private Date beginTime;
	
	private Date overTime;
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public List<HashMap<String, Object>> getBuyBankTypeList() {
		return buyBankTypeList;
	}

	public void setBuyBankTypeList(List<HashMap<String, Object>> buyBankTypeList) {
		this.buyBankTypeList = buyBankTypeList;
	}

	public List<HashMap<String, Object>> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
	}
}
