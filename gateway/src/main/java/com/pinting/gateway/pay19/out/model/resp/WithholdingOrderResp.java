package com.pinting.gateway.pay19.out.model.resp;

public class WithholdingOrderResp extends HoldingOrderBaseResp {

	private static final long serialVersionUID = -3914587787244605827L;
	
	private String tradeStatus;
	
	private String mpOrderId;
	
	private String finishTime;

	public String getTradeStatus() {
		return tradeStatus;
	}

	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}

	public String getMpOrderId() {
		return mpOrderId;
	}

	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

}
