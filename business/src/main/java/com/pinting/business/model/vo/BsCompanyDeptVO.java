package com.pinting.business.model.vo;

import com.pinting.business.model.BsCompanyDept;

public class BsCompanyDeptVO extends BsCompanyDept {
	private Integer pageNum = 1;

	private Integer numPerPage = 100;

	private Integer start = 0;
	
	private String parentCode;
	
	private String parentName;
	
	private Integer employeeId;
	
	private Integer deptId;
	
	private String startTime;
	
	private String endTime;
	
	private String deptDetail;
	
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
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDeptDetail() {
		return deptDetail;
	}

	public void setDeptDetail(String deptDetail) {
		this.deptDetail = deptDetail;
	}
	
	
}
