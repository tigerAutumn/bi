package com.pinting.business.model.vo;

import com.pinting.business.model.BsOperationReport;

public class BsOperationReportVO extends BsOperationReport{
	
    private Integer pageNum = 1;
    
    private Integer numPerPage = 100;
    
    private Integer start = 0;
    
    private String operationName;
    
    private Integer rowno;

    private String displayYear;
    
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
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		this.start = start;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public String getDisplayYear() {
		return displayYear;
	}

	public void setDisplayYear(String displayYear) {
		this.displayYear = displayYear;
	}
    
}
