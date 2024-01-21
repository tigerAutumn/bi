package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsActiveUserRecord_GetList extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6468387377883179443L;
	
	private String agents;
	
	private String agentsFlag;

	private String startDate;
	
    private String endDate;
    
    private Integer pageNum = 1;
    
    private Integer numPerPage = 50;
    
    private Integer start = 1;

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

	public Integer getStart() {
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}


	public String getAgents() {
		return agents;
	}

	public void setAgents(String agents) {
		this.agents = agents;
	}

	public String getAgentsFlag() {
		return agentsFlag;
	}

	public void setAgentsFlag(String agentsFlag) {
		this.agentsFlag = agentsFlag;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
