package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RedPacket_RedPacketStatistics extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5915183277018828928L;
	
	private ArrayList<HashMap<String, Object>> dataGrid;
	
    private Integer count;
    
    private Double budgetTotal; //预算总金额
    
    private Double budgetAmount; //预算余额
    
    private ArrayList<HashMap<String, Object>> rpNameGrid; //预算来源
    
    private ArrayList<HashMap<String, Object>> applicantGrid; //申请人

	public ArrayList<HashMap<String, Object>> getDataGrid() {
		return dataGrid;
	}

	public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
		this.dataGrid = dataGrid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public ArrayList<HashMap<String, Object>> getRpNameGrid() {
		return rpNameGrid;
	}

	public void setRpNameGrid(ArrayList<HashMap<String, Object>> rpNameGrid) {
		this.rpNameGrid = rpNameGrid;
	}

	public ArrayList<HashMap<String, Object>> getApplicantGrid() {
		return applicantGrid;
	}

	public void setApplicantGrid(ArrayList<HashMap<String, Object>> applicantGrid) {
		this.applicantGrid = applicantGrid;
	}

	public Double getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(Double budgetTotal) {
		this.budgetTotal = budgetTotal;
	}

	public Double getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(Double budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
    
	
}
