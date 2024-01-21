package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_UserInvestQuery extends ResMsg{

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -6126053152629209043L;
	
	private List<HashMap<String,Object>> userInvestList;
	
	private int totalRows;

	public List<HashMap<String, Object>> getUserInvestList() {
		return userInvestList;
	}

	public void setUserInvestList(List<HashMap<String, Object>> userInvestList) {
		this.userInvestList = userInvestList;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
}
