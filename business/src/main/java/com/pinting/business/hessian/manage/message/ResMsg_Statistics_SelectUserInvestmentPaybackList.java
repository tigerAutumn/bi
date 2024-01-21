package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_SelectUserInvestmentPaybackList extends ResMsg {
	
	private static final long serialVersionUID = -3568648965221038652L;
	
	private List<HashMap<String, Object>> userBackSectionList;
	
	private List<HashMap<String, Object>> productList;
	
	private List<HashMap<String, Object>> agentList;
	
	private List<HashMap<String, Object>> rateList;
	
	private List<HashMap<String, Object>> buyBankTypeList;
	
	private Integer numPerPage;
	
	private Integer totalRows;
	
	private Integer pageNum;
	
	private Double sumSettlementAmount;
	
	private Double settlementAmount;

	public List<HashMap<String, Object>> getUserBackSectionList() {
		return userBackSectionList;
	}

	public void setUserBackSectionList(
			List<HashMap<String, Object>> userBackSectionList) {
		this.userBackSectionList = userBackSectionList;
	}

	public List<HashMap<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(List<HashMap<String, Object>> productList) {
		this.productList = productList;
	}

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

	public Double getSumSettlementAmount() {
		return sumSettlementAmount;
	}

	public void setSumSettlementAmount(Double sumSettlementAmount) {
		this.sumSettlementAmount = sumSettlementAmount;
	}

	public Double getSettlementAmount() {
		return settlementAmount;
	}

	public void setSettlementAmount(Double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public List<HashMap<String, Object>> getAgentList() {
		return agentList;
	}

	public void setAgentList(List<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
	}

	public List<HashMap<String, Object>> getRateList() {
		return rateList;
	}

	public void setRateList(List<HashMap<String, Object>> rateList) {
		this.rateList = rateList;
	}

	public List<HashMap<String, Object>> getBuyBankTypeList() {
		return buyBankTypeList;
	}

	public void setBuyBankTypeList(List<HashMap<String, Object>> buyBankTypeList) {
		this.buyBankTypeList = buyBankTypeList;
	}

	
}
