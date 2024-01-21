package com.pinting.open.pojo.model.asset;

import java.util.Date;

public class InvestEarnings {
	private String   earningsDate	;//投资收益时间	必填	String
	private Double  amount		;//金额		必填	Double
	private String   remark		;//备注		可选	String

	
	public String getEarningsDate() {
		return earningsDate;
	}
	public void setEarningsDate(String earningsDate) {
		this.earningsDate = earningsDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
}
