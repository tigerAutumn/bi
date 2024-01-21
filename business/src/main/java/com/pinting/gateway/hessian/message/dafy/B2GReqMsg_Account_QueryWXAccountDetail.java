package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Account_QueryWXAccountDetail extends ReqMsg{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5925818542991760887L;

	private Date startDate; 

	private Date endDate; 
	
	private String transType; 

	private String pageIndex; 

	private String pageNum; 

	private String nStatu = "1";//1-成功 0失败，-1 全部
	
	public String getnStatu() {
		return nStatu;
	}

	public void setnStatu(String nStatu) {
		this.nStatu = nStatu;
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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
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
