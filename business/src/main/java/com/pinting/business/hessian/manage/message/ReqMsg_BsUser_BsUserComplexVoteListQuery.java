package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUser_BsUserComplexVoteListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8113030954777295244L;
	
	private Integer userId;
	
	/** 投资开始时间 */
	private Date sInterestBeginDate;
	
	private Date eInterestBeginDate;
	
	/** 回款开始时间 */
	private Date sLastFinishInterestDate;
	
	private Date eLastFinishInterestDate;
	
	private int pageNum;
	
	/** 每页显示的记录数(默认为20条,可以通过set改变其数量)*/
	private int numPerPage = 20;
	
	/** 排序 */
	private String orderField;
	
	private String orderDirection;
	
	private String queryDateFlag;
	
	private Integer totalRows;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getsInterestBeginDate() {
		return sInterestBeginDate;
	}

	public void setsInterestBeginDate(Date sInterestBeginDate) {
		this.sInterestBeginDate = sInterestBeginDate;
	}

	public Date geteInterestBeginDate() {
		return eInterestBeginDate;
	}

	public void seteInterestBeginDate(Date eInterestBeginDate) {
		this.eInterestBeginDate = eInterestBeginDate;
	}

	public Date getsLastFinishInterestDate() {
		return sLastFinishInterestDate;
	}

	public void setsLastFinishInterestDate(Date sLastFinishInterestDate) {
		this.sLastFinishInterestDate = sLastFinishInterestDate;
	}

	public Date geteLastFinishInterestDate() {
		return eLastFinishInterestDate;
	}

	public void seteLastFinishInterestDate(Date eLastFinishInterestDate) {
		this.eLastFinishInterestDate = eLastFinishInterestDate;
	}

	public int getPageNum() {
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

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public String getQueryDateFlag() {
		return queryDateFlag;
	}

	public void setQueryDateFlag(String queryDateFlag) {
		this.queryDateFlag = queryDateFlag;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	
}
