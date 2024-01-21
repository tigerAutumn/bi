package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MUserMainOperation_UserMainOperationListQuery extends ResMsg {
	
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -6077497964942552048L;

	private Integer totalRows;
	
	private List<HashMap<String, Object>> userMainOperationList;

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public List<HashMap<String, Object>> getUserMainOperationList() {
		return userMainOperationList;
	}

	public void setUserMainOperationList(
			List<HashMap<String, Object>> userMainOperationList) {
		this.userMainOperationList = userMainOperationList;
	}
}
