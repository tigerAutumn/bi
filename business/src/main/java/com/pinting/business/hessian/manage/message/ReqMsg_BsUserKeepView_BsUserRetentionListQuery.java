package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUserKeepView_BsUserRetentionListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -597495405587105995L;
	
	private Date sRegisterTime;
	
	private Date eRegisterTime;
	
	private String agentIds;
	
	private String nonAgentId;
	
	private int pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;
	
	private Integer totalRows;

	public Date getsRegisterTime() {
		return sRegisterTime;
	}

	public void setsRegisterTime(Date sRegisterTime) {
		this.sRegisterTime = sRegisterTime;
	}

	public Date geteRegisterTime() {
		return eRegisterTime;
	}

	public void seteRegisterTime(Date eRegisterTime) {
		this.eRegisterTime = eRegisterTime;
	}

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
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

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
