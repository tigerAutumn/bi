package com.pinting.gateway.pay19.out.model.resp;

public class QueryWithHoldingNewResp extends HoldingOrderBaseResp {

	private static final long serialVersionUID = 673722037807166940L;

	private String commonRetrievedParam;
	
	private String mpOrderId;
	
	private String orderStatus;
	
	private String finishTime;

	public String getCommonRetrievedParam() {
		return commonRetrievedParam;
	}

	public void setCommonRetrievedParam(String commonRetrievedParam) {
		this.commonRetrievedParam = commonRetrievedParam;
	}

	public String getMpOrderId() {
		return mpOrderId;
	}

	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
}
