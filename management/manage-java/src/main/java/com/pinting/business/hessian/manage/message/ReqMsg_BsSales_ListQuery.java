package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.util.StringUtil;

public class ReqMsg_BsSales_ListQuery extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5536109313773724184L;
	
	private String pageNum;
	private String numPerPage = "200";
	private String orderField;
	private String orderDirection;
	private Integer totalRows;
	
	private String salesName;
	private String startTime;
	private String endTime;
	private String queryFlag;
	
	private Integer mUserId;
	
	private String deptName;
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
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
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public String getSalesName() {
		return StringUtil.isBlank(salesName) ? null : StringUtil.trimStr(salesName);
	}
	public void setSalesName(String salesName) {
		this.salesName = StringUtil.isBlank(salesName) ? null : StringUtil.trimStr(salesName);
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
	public String getQueryFlag() {
		return queryFlag;
	}
	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}
	public Integer getmUserId() {
		return mUserId;
	}
	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	
}
