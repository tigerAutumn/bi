package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSales_UserListQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8622657159774686960L;
	
	private ArrayList<HashMap<String, Object>> userSalesList;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	
	public ArrayList<HashMap<String, Object>> getUserSalesList() {
		return userSalesList;
	}
	public void setUserSalesList(ArrayList<HashMap<String, Object>> userSalesList) {
		this.userSalesList = userSalesList;
	}
	public String getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(String totalRows) {
		this.totalRows = totalRows;
	}
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

}
