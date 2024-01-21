package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_TransDetail_QueryByUserId extends ResMsg{

	/**
	 * 交易明细
	 */
	private static final long serialVersionUID = -6642128369096114980L;
	private Integer userId;
	private Double totalEarnings;
	private Integer totalCount;
	private Integer pageIndex;
	private Integer processingNum;//当前用户处理中交易的数量（银行购买包括充值和购买，算一笔）
	private ArrayList<HashMap<String, Object>> transPrincipals;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getTotalEarnings() {
		return totalEarnings;
	}
	public void setTotalEarnings(Double totalEarnings) {
		this.totalEarnings = totalEarnings;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public ArrayList<HashMap<String, Object>> getTransPrincipals() {
		return transPrincipals;
	}
	public void setTransPrincipals(
			ArrayList<HashMap<String, Object>> transPrincipals) {
		this.transPrincipals = transPrincipals;
	}
	public Integer getProcessingNum() {
		return processingNum;
	}
	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}
}
