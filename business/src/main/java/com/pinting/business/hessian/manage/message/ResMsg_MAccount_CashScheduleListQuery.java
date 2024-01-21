package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAccount_CashScheduleListQuery extends ResMsg{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -49242450863732710L;

	private ArrayList<HashMap<String,Object>> valueList;

	private Date beginTime;
	
	private Date overTime;
	
	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	/** 未来30天应付本金合计 */
	private Double totalCashBaseAmount30;
	
	/** 未来30天应付利息合计 */
	private Double totalBashInterestAmount30;
	
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

	public ArrayList<HashMap<String, Object>> getValueList() {
		return valueList;
	}

	public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
		this.valueList = valueList;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Double getTotalCashBaseAmount30() {
		return totalCashBaseAmount30;
	}

	public void setTotalCashBaseAmount30(Double totalCashBaseAmount30) {
		this.totalCashBaseAmount30 = totalCashBaseAmount30;
	}

	public Double getTotalBashInterestAmount30() {
		return totalBashInterestAmount30;
	}

	public void setTotalBashInterestAmount30(Double totalBashInterestAmount30) {
		this.totalBashInterestAmount30 = totalBashInterestAmount30;
	}

	
	
}
