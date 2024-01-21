package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Account_AccountJnlListQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7587822402980070142L;
	private Integer userId;
	private Double totalEarnings;
	private Integer totalCount;
	private Integer pageIndex;
	
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

	/**
	 * 以下循环：
	 * transTime	交易时间	必填	Date
	 * transName		交易名称		必填	Double
	 * transAmount		交易金额		可选	String
	 * afterAvialableBlance		剩余金额		可选	String
	 * cdFlag1		借贷		1代表借 2代表贷
	 */
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

	public ArrayList<HashMap<String, Object>> getTransPrincipals() {
		return transPrincipals;
	}

	public void setTransPrincipals(
			ArrayList<HashMap<String, Object>> transPrincipals) {
		this.transPrincipals = transPrincipals;
	}

	
}
