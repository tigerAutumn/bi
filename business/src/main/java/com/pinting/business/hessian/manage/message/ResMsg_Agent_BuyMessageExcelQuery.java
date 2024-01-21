package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Agent_BuyMessageExcelQuery extends ResMsg {
	
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 8229570620386759812L;
	private List<HashMap<String, Object>> userBuyMessageList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	
	public List<HashMap<String, Object>> getUserBuyMessageList() {
		return userBuyMessageList;
	}
	public void setUserBuyMessageList(
			List<HashMap<String, Object>> userBuyMessageList) {
		this.userBuyMessageList = userBuyMessageList;
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
