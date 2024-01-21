package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_AgentPerformance extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8084549504070102963L;
	
	private ArrayList<HashMap<String, Object>> valueList;
	
	private List<HashMap<String, Object>> agentList;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	private Integer numPerPage;
	
	/** 年化金额合计 */
	private Double proceedsBalanceTotal;

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public List<HashMap<String, Object>> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
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

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Double getProceedsBalanceTotal() {
		return proceedsBalanceTotal;
	}

	public void setProceedsBalanceTotal(Double proceedsBalanceTotal) {
		this.proceedsBalanceTotal = proceedsBalanceTotal;
	}

}
