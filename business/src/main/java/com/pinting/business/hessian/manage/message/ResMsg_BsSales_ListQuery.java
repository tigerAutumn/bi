package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSales_ListQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3875476338517804278L;
	
	private ArrayList<HashMap<String, Object>> salesList;
	private List<HashMap<String, Object>> deptList;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	
	public ArrayList<HashMap<String, Object>> getSalesList() {
		return salesList;
	}
	public void setSalesList(ArrayList<HashMap<String, Object>> salesList) {
		this.salesList = salesList;
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
	public List<HashMap<String, Object>> getDeptList() {
		return deptList;
	}
	public void setDeptList(List<HashMap<String, Object>> deptList) {
		this.deptList = deptList;
	}

}
