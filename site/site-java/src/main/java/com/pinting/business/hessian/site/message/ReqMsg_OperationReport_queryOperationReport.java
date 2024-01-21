package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询运营报告列表信息 入参
 * @author shiyulong
 * @2016-7-6 下午5:21:44
 */
public class ReqMsg_OperationReport_queryOperationReport  extends ReqMsg  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7011703017473518962L;
	
	/**
	 * 年份
	 */
	private String year;
	
	private Integer page;
	  
    private Integer pageNum = 1;
    
    private Integer numPerPage = 10;
    
    private Integer start = 1;
    
    private Integer totalPages; // 总页数
    
    private Integer totolRows = 0;  // 总数据数
    
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

    public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
    
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
    
}
