package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.InvestEarnings;

public class DayInvestEarningsResponse extends Response {
	private Integer totalPage;  //总数
	
	/**
	 * 以下循环：
	 * earningsDate	投资时间	必填	String
	 * amount		金额		必填	Double
	 * remark		备注		可选	String
	 */
	private List<InvestEarnings> investEarnings;


	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public List<InvestEarnings> getInvestEarnings() {
		return investEarnings;
	}

	public void setInvestEarnings(List<InvestEarnings> investEarnings) {
		this.investEarnings = investEarnings;
	}
	
}
