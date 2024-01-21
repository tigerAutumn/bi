package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Statistics_AgentPerformance extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7010454140716540151L;
	
	/** 开始时间 */
	private Date beginTime;
	
	/** 结束时间 */
	private Date overTime;
	
    private int pageNum = 1;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;
	
	private String queryDefaultPageFlag;
	
	private String proceedsBalance;
	
	private String dept;

	// 终端
	private String terminal;
	
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

	public int getPageNum() {
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

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getQueryDefaultPageFlag() {
		return queryDefaultPageFlag;
	}

	public void setQueryDefaultPageFlag(String queryDefaultPageFlag) {
		this.queryDefaultPageFlag = queryDefaultPageFlag;
	}

	public String getProceedsBalance() {
		return proceedsBalance;
	}

	public void setProceedsBalance(String proceedsBalance) {
		this.proceedsBalance = proceedsBalance;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	
}
