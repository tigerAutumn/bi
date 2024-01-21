package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Statistics_BuyMessageListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3568648965221038652L;
	
	private List<HashMap<String, Object>> userBuyMessageList;
	private List<HashMap<String, Object>> productList;
	private List<HashMap<String, Object>> agentList;
	private List<HashMap<String, Object>> buyBankTypeList;
	private List<HashMap<String, Object>> rateList;
	private List<HashMap<String, Object>> sysConfigs;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	private Double sumBalanceAmount;
	
	public List<HashMap<String, Object>> getProductList() {
		return productList;
	}
	public void setProductList(List<HashMap<String, Object>> productList) {
		this.productList = productList;
	}
	
	public List<HashMap<String, Object>> getAgentList() {
		return agentList;
	}
	public void setAgentList(List<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
	}
	
	
	public List<HashMap<String, Object>> getBuyBankTypeList() {
		return buyBankTypeList;
	}
	public void setBuyBankTypeList(List<HashMap<String, Object>> buyBankTypeList) {
		this.buyBankTypeList = buyBankTypeList;
	}
	public Double getSumBalanceAmount() {
		return sumBalanceAmount;
	}
	public void setSumBalanceAmount(Double sumBalanceAmount) {
		this.sumBalanceAmount = sumBalanceAmount;
	}
	public List<HashMap<String, Object>> getUserBuyMessageList() {
		return userBuyMessageList;
	}
	public void setUserBuyMessageList(
			List<HashMap<String, Object>> userBuyMessageList) {
		this.userBuyMessageList = userBuyMessageList;
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
	public List<HashMap<String, Object>> getRateList() {
		return rateList;
	}
	public void setRateList(List<HashMap<String, Object>> rateList) {
		this.rateList = rateList;
	}
	public List<HashMap<String, Object>> getSysConfigs() {
		return sysConfigs;
	}
	public void setSysConfigs(List<HashMap<String, Object>> sysConfigs) {
		this.sysConfigs = sysConfigs;
	}
	
	
	
	
}
