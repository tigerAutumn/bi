package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 2016公司年会抽奖   入参
 * Created by shh on 2017/01/13 15:08.
 */
public class ReqMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3812613833763894276L;
	
	/* 员工名字 */
	private String employeeName;
	
	/* 是否中奖 */
	private String isWin;
	
	/* 奖品id */
	private String activityAwardId;
	
	private int pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getIsWin() {
		return isWin;
	}

	public void setIsWin(String isWin) {
		this.isWin = isWin;
	}

	public String getActivityAwardId() {
		return activityAwardId;
	}

	public void setActivityAwardId(String activityAwardId) {
		this.activityAwardId = activityAwardId;
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
	
}
