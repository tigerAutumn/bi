package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 银行卡管理
 * @author caonengwen
 *
 */
public class ResMsg_BsBankCard_BankCardListQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8516133915551958655L;
	
	private ArrayList<HashMap<String, Object>> bankCards;
	private ArrayList<HashMap<String, Object>> bankNameList;
	private String totalRows;
	private String pageNum;
	private String numPerPage;

	public ArrayList<HashMap<String, Object>> getBankCards() {
		return bankCards;
	}
	public void setBankCards(ArrayList<HashMap<String, Object>> bankCards) {
		this.bankCards = bankCards;
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
	public ArrayList<HashMap<String, Object>> getBankNameList() {
		return bankNameList;
	}
	public void setBankNameList(ArrayList<HashMap<String, Object>> bankNameList) {
		this.bankNameList = bankNameList;
	}
	
}
