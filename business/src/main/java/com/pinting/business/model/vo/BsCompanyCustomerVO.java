package com.pinting.business.model.vo;

import com.pinting.business.model.BsCompanyCustomer;

public class BsCompanyCustomerVO extends BsCompanyCustomer{
	
	private Integer pageNum = 1;

	private Integer numPerPage = 100;

	private Integer start = 1;

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
	
	
}
