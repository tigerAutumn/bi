package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 *19付银行渠道维护
 * @author caonengwen
 *
 */
public class ResMsg_Bs19PayBank_BankListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8703643660011400452L;
	
	private ArrayList<HashMap<String, Object>> bs19PayBank;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	public ArrayList<HashMap<String, Object>> getBs19PayBank() {
		return bs19PayBank;
	}
	public void setBs19PayBank(ArrayList<HashMap<String, Object>> bs19PayBank) {
		this.bs19PayBank = bs19PayBank;
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
