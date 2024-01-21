package com.pinting.business.hessian.manage.message;


import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_AgentQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8330779915443818882L;
	private List<HashMap<String, Object>> agentList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	public List<HashMap<String, Object>> getAgentList() {
		return agentList;
	}
	public void setAgentList(List<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
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


}
