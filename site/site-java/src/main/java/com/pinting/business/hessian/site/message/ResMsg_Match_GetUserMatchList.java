package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据userId和产品id分页查询债权明细 出参
 * @author bianyatian
 * @2016-4-22 上午10:37:01
 */
public class ResMsg_Match_GetUserMatchList extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4390824441186755652L;
	/*债权列表*/
	private ArrayList<HashMap<String, Object>> dataGrid;
	
	private int total;  //总页数
	
	private int pageIndex; //当前页码
	
	private String propertySymbol;//资金接收方标记
	
	private String entrustStatus; //委托计划状态
	
	private Double entrustAmount; //出借受让金额

	private String debtDetailsFlag; //3月10号后，云贷、7贷老产品重新匹配后 页面债权明细文案显示标志

	public ArrayList<HashMap<String, Object>> getDataGrid() {
		return dataGrid;
	}
	public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
		this.dataGrid = dataGrid;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPropertySymbol() {
		return propertySymbol;
	}
	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}
	public String getEntrustStatus() {
		return entrustStatus;
	}
	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}
	public Double getEntrustAmount() {
		return entrustAmount;
	}
	public void setEntrustAmount(Double entrustAmount) {
		this.entrustAmount = entrustAmount;
	}

	public String getDebtDetailsFlag() {
		return debtDetailsFlag;
	}

	public void setDebtDetailsFlag(String debtDetailsFlag) {
		this.debtDetailsFlag = debtDetailsFlag;
	}
}
