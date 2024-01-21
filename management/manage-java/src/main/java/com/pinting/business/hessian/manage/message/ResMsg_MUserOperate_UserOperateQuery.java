package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MUserOperate_UserOperateQuery extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -4623479475625277680L;
	
	private List<HashMap<String, Object>> userOperateList;
	
	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	private Integer receiveNum;

	public List<HashMap<String, Object>> getUserOperateList() {
		return userOperateList;
	}

	public void setUserOperateList(List<HashMap<String, Object>> userOperateList) {
		this.userOperateList = userOperateList;
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

	public Integer getReceiveNum() {
		return receiveNum;
	}

	public void setReceiveNum(Integer receiveNum) {
		this.receiveNum = receiveNum;
	}
}
