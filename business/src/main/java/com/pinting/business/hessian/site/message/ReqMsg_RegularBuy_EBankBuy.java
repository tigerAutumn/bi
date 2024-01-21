package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RegularBuy_EBankBuy extends ReqMsg {

	private static final long serialVersionUID = 4253693363497692646L;

	private Integer userId;
	
	private Integer productId;
	
	private Double amount;
	
	private Integer transType; //交易类型1卡购买,2充值
	
	private String bankCode;
	
	private Integer redPacketId; //红包ID

	private String webFlag;//前端标识

	public String getWebFlag() {
		return webFlag;
	}

	public void setWebFlag(String webFlag) {
		this.webFlag = webFlag;
	}

	public Integer getRedPacketId() {
		return redPacketId;
	}

	public void setRedPacketId(Integer redPacketId) {
		this.redPacketId = redPacketId;
	}

	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	
	
}
