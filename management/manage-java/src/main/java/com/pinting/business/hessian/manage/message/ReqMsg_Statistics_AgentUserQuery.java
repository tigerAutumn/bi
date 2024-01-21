package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Statistics_AgentUserQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1693879388034009798L;

	private Integer agentId;
	private String mobile;
	private String userName;
	private String investFlag;
	private String sregistTime;
	private String eregistTime;
	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	
	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getInvestFlag() {
		return investFlag;
	}

	public void setInvestFlag(String investFlag) {
		this.investFlag = investFlag;
	}

	public String getSregistTime() {
		return sregistTime;
	}

	public void setSregistTime(String sregistTime) {
		this.sregistTime = sregistTime;
	}

	public String getEregistTime() {
		return eregistTime;
	}

	public void setEregistTime(String eregistTime) {
		this.eregistTime = eregistTime;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public int getPageNum() {
		if (pageNum < 1) {

			this.pageNum = 1;
		}

		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}


}
