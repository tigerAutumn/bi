package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_RedPacket_GrantManagermentGrid extends ResMsg {
   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8233458761027593404L;

	private ArrayList<HashMap<String, Object>> dataGrid;
    
    private Integer count;
    
    private Double budgetBalance;

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

	public Double getBudgetBalance() {
		return budgetBalance;
	}

	public void setBudgetBalance(Double budgetBalance) {
		this.budgetBalance = budgetBalance;
	}
    
    
}

