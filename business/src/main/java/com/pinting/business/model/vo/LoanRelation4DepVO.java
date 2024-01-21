package com.pinting.business.model.vo;

import com.pinting.business.model.LnLoanRelation;

/**
 * 存管产品
 * 债权匹配返回列表
 * @author bianyatian
 * @2017-4-1 下午8:16:39
 */
public class LoanRelation4DepVO{
	
	private LnLoanRelation LnLoanRelation;
	
	private Double couponAmount;	//抵用券金额(仅定期)
	
	private Double selfAmount;	//自费金额(仅定期)
	
	private String hfUserId;	//恒丰用户id
	
	private Integer bsSubAccountId_red; //抵用红包对应子账户

	public Double getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Double couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Double getSelfAmount() {
		return selfAmount;
	}

	public void setSelfAmount(Double selfAmount) {
		this.selfAmount = selfAmount;
	}

	public String getHfUserId() {
		return hfUserId;
	}

	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}

	public Integer getBsSubAccountId_red() {
		return bsSubAccountId_red;
	}

	public void setBsSubAccountId_red(Integer bsSubAccountId_red) {
		this.bsSubAccountId_red = bsSubAccountId_red;
	}

	public LnLoanRelation getLnLoanRelation() {
		return LnLoanRelation;
	}

	public void setLnLoanRelation(LnLoanRelation lnLoanRelation) {
		LnLoanRelation = lnLoanRelation;
	}

}
