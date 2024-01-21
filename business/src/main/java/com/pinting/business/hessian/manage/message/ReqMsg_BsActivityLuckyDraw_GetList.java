package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsActivityLuckyDraw_GetList extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2495022049038697401L;

	private String agents;
	
	private String agentsFlag;
	
	private String mobile;
	
	private String drawTimeStart;
	  
	private String drawTimeEnd;
	 
	private String isUserDraw;
	
	private String awardContent;
	
	private String isWin;
	
	private Integer pageNum = 1;
    
    private Integer numPerPage = 50;
    
    private Integer start = 1;

	private String userName;

	/* 活动名称 */
	private String activityName;

	/* 总记录条数 */
	private Integer count;
    
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDrawTimeStart() {
		return drawTimeStart;
	}

	public void setDrawTimeStart(String drawTimeStart) {
		this.drawTimeStart = drawTimeStart;
	}

	public String getDrawTimeEnd() {
		return drawTimeEnd;
	}

	public void setDrawTimeEnd(String drawTimeEnd) {
		this.drawTimeEnd = drawTimeEnd;
	}

	public String getIsUserDraw() {
		return isUserDraw;
	}

	public void setIsUserDraw(String isUserDraw) {
		this.isUserDraw = isUserDraw;
	}

	public String getAwardContent() {
		return awardContent;
	}

	public void setAwardContent(String awardContent) {
		this.awardContent = awardContent;
	}

	public String getIsWin() {
		return isWin;
	}

	public void setIsWin(String isWin) {
		this.isWin = isWin;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
