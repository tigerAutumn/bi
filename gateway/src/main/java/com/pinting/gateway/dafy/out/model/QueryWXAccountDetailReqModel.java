package com.pinting.gateway.dafy.out.model;

import java.util.Date;


public class QueryWXAccountDetailReqModel extends BaseReqModel {
	private Date startDate; 
	private Date endDate; 
	private String pageIndex; 
	private String pageNum; 
	private String transType;
	private String nStatu="1"; 

	public String getnStatu() {
		return nStatu;
	}

	public void setnStatu(String nStatu) {
		this.nStatu = nStatu;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	

}
