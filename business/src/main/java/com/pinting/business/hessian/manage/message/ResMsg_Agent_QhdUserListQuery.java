package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年7月18日 上午10:36:06
 */
public class ResMsg_Agent_QhdUserListQuery extends ResMsg {

	private static final long serialVersionUID = -6752822894171514255L;

	private List<HashMap<String, Object>> bsUserList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	
	private Integer mAgentId;

	public List<HashMap<String, Object>> getBsUserList() {
		return bsUserList;
	}

	public void setBsUserList(List<HashMap<String, Object>> bsUserList) {
		this.bsUserList = bsUserList;
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

	public Integer getmAgentId() {
		return mAgentId;
	}

	public void setmAgentId(Integer mAgentId) {
		this.mAgentId = mAgentId;
	}
	
}
