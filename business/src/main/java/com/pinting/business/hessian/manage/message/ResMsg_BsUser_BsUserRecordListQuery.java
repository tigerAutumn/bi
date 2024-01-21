package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUser_BsUserRecordListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6244335085626492675L;

	private List<HashMap<String, Object>> recordList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	public List<HashMap<String, Object>> getRecordList() {
		return recordList;
	}
	public void setRecordList(List<HashMap<String, Object>> recordList) {
		this.recordList = recordList;
	}
	public Integer getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}
	public Integer getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
