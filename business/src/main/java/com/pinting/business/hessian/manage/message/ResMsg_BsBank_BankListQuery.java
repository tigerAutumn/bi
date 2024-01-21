package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 银行维护
 * @author caonengwen
 *
 */
public class ResMsg_BsBank_BankListQuery extends ResMsg{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4070958951005491924L;
	private ArrayList<HashMap<String, Object>> banks;
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	
	public ArrayList<HashMap<String, Object>> getBanks() {
		return banks;
	}
	public void setBanks(ArrayList<HashMap<String, Object>> banks) {
		this.banks = banks;
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
