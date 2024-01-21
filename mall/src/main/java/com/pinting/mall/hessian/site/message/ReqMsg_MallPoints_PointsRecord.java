package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 积分记录
 * @project site-java
 * @author bianyatian
 * @2018-5-16 上午10:46:41
 */
public class ReqMsg_MallPoints_PointsRecord extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4373662254086255212L;

	private Integer userId;
	
	private Integer pageNum = 1;
	    
	private Integer numPerPage = 10;
	
	private Integer start = 1;
	
	private Integer totalPages; // 总页数
	
	private Integer totolRows = 0;  // 总数据数

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getTotalPages() {
      totalPages = (int) Math.ceil((double) totolRows / numPerPage);
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
