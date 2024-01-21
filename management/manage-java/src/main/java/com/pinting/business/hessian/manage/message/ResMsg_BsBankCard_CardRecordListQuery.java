package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsBankCard_CardRecordListQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5483015511951480445L;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	private ArrayList<HashMap<String, Object>> payOrders;
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
	public ArrayList<HashMap<String, Object>> getPayOrders() {
		return payOrders;
	}
	public void setPayOrders(ArrayList<HashMap<String, Object>> payOrders) {
		this.payOrders = payOrders;
	}
}
