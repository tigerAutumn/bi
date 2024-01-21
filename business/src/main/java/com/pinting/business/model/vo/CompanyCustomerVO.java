package com.pinting.business.model.vo;

import com.pinting.business.model.BsCompanyCustomer;

/**
 * 
 * @author HuanXiong
 * @version 2016-5-30 下午1:59:14
 */
public class CompanyCustomerVO extends BsCompanyCustomer {

    private String parentName;  // 上级用户名称
    
    private Integer pageNum = 1;
    
    private Integer numPerPage = 100;
    
    private Integer start = 1;
    
    private Integer totalPages; // 总页数
    
    private Integer totolRows = 0;  // 总数据数
    
    
	private String startTime;
	
	private String endTime;
	
	private String customerDetail;

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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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

	public String getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(String customerDetail) {
		this.customerDetail = customerDetail;
	}
    
    
}
