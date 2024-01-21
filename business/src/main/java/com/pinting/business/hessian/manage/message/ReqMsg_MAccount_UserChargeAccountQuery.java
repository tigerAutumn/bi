package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_UserChargeAccountQuery extends ReqMsg {
	
	private static final long serialVersionUID = -6987339564590137076L;

	private Date beginTime;

	private Date overTime;

	private int pageNum;

	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;

	// 排序
	private String orderField;

	private String orderDirection;

	private String userName;
	
	private String mobile;
	
	/** 账务开始时间 */
	private Date startTransTime;

	/** 账务结束时间 */
	private Date endTransTime;
	
	private String queryFlag;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getStartTransTime() {
		return startTransTime;
	}

	public void setStartTransTime(Date startTransTime) {
		this.startTransTime = startTransTime;
	}

	public Date getEndTransTime() {
		return endTransTime;
	}

	public void setEndTransTime(Date endTransTime) {
		this.endTransTime = endTransTime;
	}

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

	
}
