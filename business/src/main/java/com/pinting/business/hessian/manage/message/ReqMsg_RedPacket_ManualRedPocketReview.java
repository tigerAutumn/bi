package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RedPacket_ManualRedPocketReview extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3741518444543021867L;
	private Integer id;
	
	private Integer pageNum = 1;
    
    private Integer numPerPage = 20;
    
    private Integer start = 1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	
}
