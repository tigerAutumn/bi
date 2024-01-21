package com.pinting.business.accounting.finance.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;

public class TransferAccountInfo {
	
	private Double outUser_principalAmount; //出让人增加本金金额
	
	private Double outUser_interestAmount; //出让人增加利息金额
	
	private Integer outUser_investorAuthActId; //出让人增加金额的子账户id
	
	private Integer outUser_diffActId; //出让人补差户
	
	private Double diffFee; //补差户减少金额
	
	private Double inUser_authAmount; //受让人站岗户支付金额
	
	private Integer inUser_investorAuthActId; //受让人站岗户id
	
	private Double inUser_redAmount; //受让人红包户支付金额
	
	private Integer inUser_investorRedActId; //受让人红包户id
	
	private Double fee; //存管体系-币港湾对云贷（赞时贷）营收子账户
	
	private PartnerEnum partnerEnum; //合作方
	
	private Integer out_relationId; //出让人债权id


	public Integer getOutUser_investorAuthActId() {
		return outUser_investorAuthActId;
	}

	public void setOutUser_investorAuthActId(Integer outUser_investorAuthActId) {
		this.outUser_investorAuthActId = outUser_investorAuthActId;
	}

	public Double getInUser_authAmount() {
		return inUser_authAmount;
	}

	public void setInUser_authAmount(Double inUser_authAmount) {
		this.inUser_authAmount = inUser_authAmount;
	}

	public Integer getInUser_investorAuthActId() {
		return inUser_investorAuthActId;
	}

	public void setInUser_investorAuthActId(Integer inUser_investorAuthActId) {
		this.inUser_investorAuthActId = inUser_investorAuthActId;
	}

	public Double getInUser_redAmount() {
		return inUser_redAmount;
	}

	public void setInUser_redAmount(Double inUser_redAmount) {
		this.inUser_redAmount = inUser_redAmount;
	}

	public Integer getInUser_investorRedActId() {
		return inUser_investorRedActId;
	}

	public void setInUser_investorRedActId(Integer inUser_investorRedActId) {
		this.inUser_investorRedActId = inUser_investorRedActId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getOutUser_principalAmount() {
		return outUser_principalAmount;
	}

	public void setOutUser_principalAmount(Double outUser_principalAmount) {
		this.outUser_principalAmount = outUser_principalAmount;
	}

	public Double getOutUser_interestAmount() {
		return outUser_interestAmount;
	}

	public void setOutUser_interestAmount(Double outUser_interestAmount) {
		this.outUser_interestAmount = outUser_interestAmount;
	}

	public Double getDiffFee() {
		return diffFee;
	}

	public void setDiffFee(Double diffFee) {
		this.diffFee = diffFee;
	}

	public Integer getOutUser_diffActId() {
		return outUser_diffActId;
	}

	public void setOutUser_diffActId(Integer outUser_diffActId) {
		this.outUser_diffActId = outUser_diffActId;
	}
	
	@Override
	public String toString() {
		return "TransferAccountInfo [outUser_principalAmount="
				+ outUser_principalAmount + ", outUser_interestAmount="
				+ outUser_interestAmount + ", outUser_investorAuthActId="
				+ outUser_investorAuthActId + ", outUser_diffActId="
				+ outUser_diffActId + ", diffFee=" + diffFee
				+ ", inUser_authAmount=" + inUser_authAmount
				+ ", inUser_investorAuthActId=" + inUser_investorAuthActId
				+ ", inUser_redAmount=" + inUser_redAmount
				+ ", inUser_investorRedActId=" + inUser_investorRedActId
				+ ", fee=" + fee + "]";
	}

	public PartnerEnum getPartnerEnum() {
		return partnerEnum;
	}

	public void setPartnerEnum(PartnerEnum partnerEnum) {
		this.partnerEnum = partnerEnum;
	}

	public Integer getOut_relationId() {
		return out_relationId;
	}

	public void setOut_relationId(Integer out_relationId) {
		this.out_relationId = out_relationId;
	}


}
