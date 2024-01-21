package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAccount_AssetsList extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2286639576570161034L;

	private ArrayList<HashMap<String,Object>> valueList;

	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;
	private int status;
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}
}
