package com.pinting.gateway.pay19.out.model.req;

public class HoldingOrderNotifyReq extends HoldingOrderBaseReq {

	private static final long serialVersionUID = 120722863195402787L;
	
	private String commonRetrievedParam;
	
	private String mpOrderId;
	
	private String orderStatus;
	
	private String retCode;
	
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

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "代扣下单接口(异步推送数据)： 【version=" + getVersion() + ", verifyString=" + getVerifyString() 
				+ ", merchantId=" + getMerchantId() + ", mxUserId=" + getMxUserId()
				+ ", mxOrderId=" + getMxOrderId() + ", mxOrderDate=" + getMxOrderDate()
				+", commonRetrievedParam=" + commonRetrievedParam + ", mpOrderId=" + mpOrderId
				+ ", orderStatus=" + orderStatus + ", retCode=" + retCode + ", finishTime=" + finishTime + "】";
	}

}
