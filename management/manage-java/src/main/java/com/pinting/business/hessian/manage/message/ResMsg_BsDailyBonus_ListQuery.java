package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsDailyBonus_ListQuery extends ResMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5051000712675020797L;
	
	private String totalRows;
	private String pageNum;
	private String numPerPage;
	private ArrayList<HashMap<String, Object>> bsDailyBonuss;
	/**奖励总金额**/
	private Double allBonus;
	
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
	public ArrayList<HashMap<String, Object>> getBsDailyBonuss() {
		return bsDailyBonuss;
	}
	public void setBsDailyBonuss(ArrayList<HashMap<String, Object>> bsDailyBonuss) {
		this.bsDailyBonuss = bsDailyBonuss;
	}
	public Double getAllBonus() {
		return allBonus;
	}
	public void setAllBonus(Double allBonus) {
		this.allBonus = allBonus;
	}
	
}
