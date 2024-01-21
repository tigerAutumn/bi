package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsRedPacketApply_BsRedPacketApplyCheckList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3275963266166348673L;
	
    private ArrayList<HashMap<String, Object>> valueList;
	
	private List<HashMap<String, Object>> creatorList; /** 红包申请人*/
	
	private Integer totalRows;
	
    private Integer pageNum;
	
	private Integer numPerPage;

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public List<HashMap<String, Object>> getCreatorList() {
		return creatorList;
	}

	public void setCreatorList(List<HashMap<String, Object>> creatorList) {
		this.creatorList = creatorList;
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

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

}
