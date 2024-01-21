package com.pinting.open.pojo.model.asset;

public class Bonuss {
	private String   earningsDate	;//奖励获得时间	必填	String
	private Double  amount		;//金额		必填	Double
	private String   remark		;//备注		可选	String
	private String detail;		//细节：   拼接加息券使用时间、加息幅度和产品名称

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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
