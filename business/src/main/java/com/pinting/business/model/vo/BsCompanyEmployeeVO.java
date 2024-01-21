package com.pinting.business.model.vo;

import com.pinting.business.model.BsCompanyEmployee;

public class BsCompanyEmployeeVO extends BsCompanyEmployee {
	
	private String deptName;
	
    private Integer pageNum = 1;
    
    private Integer numPerPage = 100;
    
    private Integer start = 1;
    
    private Integer totalPages; // 总页数
    
    private Integer totolRows = 0;  // 总数据数
    
    
	private String startTime;
	
	private String endTime;

	public Integer getPageNum() {
        if (pageNum < 1) 
            this.pageNum = 1;
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

	public Integer getTotalPages() {
        totalPages = (totolRows - totolRows % numPerPage) / numPerPage;
        if(totolRows % numPerPage > 0) 
            totalPages ++;
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getTotolRows() {
		return totolRows;
	}

	public void setTotolRows(Integer totolRows) {
		this.totolRows = totolRows;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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
    
    
}
