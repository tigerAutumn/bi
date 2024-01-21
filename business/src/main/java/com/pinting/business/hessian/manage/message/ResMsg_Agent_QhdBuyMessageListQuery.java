package com.pinting.business.hessian.manage.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 渠道用户投资查询（秦皇岛）
 * @author SHENGUOPING
 * @date  2018年7月18日 下午1:40:18
 */
public class ResMsg_Agent_QhdBuyMessageListQuery extends ResMsg {

	private static final long serialVersionUID = -3680993286032236781L;
	
	private List<HashMap<String, Object>> userBuyMessageList;
	private List<HashMap<String, Object>> productList;
	private Integer numPerPage;
	private Integer totalRows;
	private Integer pageNum;
	private Double sumBalanceAmount;
	private Integer agentId;
	
	public List<HashMap<String, Object>> getProductList() {
		return productList;
	}
	public void setProductList(List<HashMap<String, Object>> productList) {
		this.productList = productList;
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
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
}
