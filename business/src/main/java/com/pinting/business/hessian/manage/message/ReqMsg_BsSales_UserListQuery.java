package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsSales_UserListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2790785000740183427L;
	
	private Integer id;
	private Integer grade;
	private String pageNum;
	private String numPerPage;
	private String salesName;
	private String startTime;
	private String endTime;
	private String startMoney;
	private String endMoney;
	private String mobile;
	private String userName;
	private Integer totalRows;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName == "" ? null : salesName.trim();
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime == "" ? null : startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == "" ? null : endTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == "" ? null : mobile.trim();
	}

	public String getStartMoney() {
		return startMoney;
	}

	public void setStartMoney(String startMoney) {
		this.startMoney = startMoney;
	}

	public String getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(String endMoney) {
		this.endMoney = endMoney;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == "" ? null : userName.trim();
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
