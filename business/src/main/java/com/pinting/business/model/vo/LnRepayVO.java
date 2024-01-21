package com.pinting.business.model.vo;

public class LnRepayVO {
	
	private Integer id; // LnRepay-id
    private String status; //LnRepay-status
	private String partnerLoanId; // LnLoan-合作方借款编号
	private String channel; // LnPayOrders-订单渠道
	private String returnMsg; // LnPayOrders-返回信息
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPartnerLoanId() {
		return partnerLoanId;
	}
	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
