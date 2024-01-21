package com.pinting.business.hessian.manage.message;


import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_AgentUserQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8388191892398780287L;
	private List<HashMap<String, Object>> agentUserList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	
	public List<HashMap<String, Object>> getAgentUserList() {
		return agentUserList;
	}
	public void setAgentUserList(List<HashMap<String, Object>> agentUserList) {
		this.agentUserList = agentUserList;
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
