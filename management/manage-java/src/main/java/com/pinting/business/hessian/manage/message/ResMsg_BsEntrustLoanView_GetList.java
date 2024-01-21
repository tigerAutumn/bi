package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.business.model.BsEntrustLoanView;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsEntrustLoanView_GetList extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3900153639266705803L;

	private ArrayList<HashMap<String,Object>> valueList;
	
	private BsEntrustLoanView VIPview;

	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
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

	public BsEntrustLoanView getVIPview() {
		return VIPview;
	}

	public void setVIPview(BsEntrustLoanView vIPview) {
		VIPview = vIPview;
	}

}
