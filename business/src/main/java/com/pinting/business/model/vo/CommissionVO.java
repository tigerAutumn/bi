package com.pinting.business.model.vo;

public class CommissionVO {

	private Double needPayAmount; //应扣

	private Double actPayAmount; //实扣

	private Double threePartyPaymentServiceFee;	// 三方支付手续费

	public Double getNeedPayAmount() {
		return needPayAmount;
	}

	public void setNeedPayAmount(Double needPayAmount) {
		this.needPayAmount = needPayAmount;
	}

	public Double getActPayAmount() {
		return actPayAmount;
	}

	public void setActPayAmount(Double actPayAmount) {
		this.actPayAmount = actPayAmount;
	}

	public Double getThreePartyPaymentServiceFee() {
		return threePartyPaymentServiceFee;
	}

	public void setThreePartyPaymentServiceFee(Double threePartyPaymentServiceFee) {
		this.threePartyPaymentServiceFee = threePartyPaymentServiceFee;
	}
}
