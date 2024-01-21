package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.MUserOpRecord;

public class MUserOpRecordVO extends MUserOpRecord {
	
	private String managerName;
	
	private String startTime;
	
	private String endTime;
	
	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为200条,可以通过set改变其数量)
	 */
	private int numPerPage = 200;
	
	private int start;

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

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getStart() {
		if (pageNum < 1) {

			this.start = 0;
		}else{
			this.start = (pageNum-1)*numPerPage;
		}
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}


}
