package com.pinting.business.accounting.loan.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.model.LnDepositionRepaySchedule;

/**
 * Created by babyshark on 2017/4/6.
 */
public class DepRepaySchedule extends LnDepositionRepaySchedule {
    private Double repayAmount; //实际还款金额
    private Double repayFee;//还款手续费
    private String hfUserId; //恒丰客户号（代偿人代偿时 传递代偿人恒丰客户号）
    private Integer targetId;//标的编号
    private PartnerEnum partnerEnum;//标的所属合作方
    private Integer bsUserId; //代偿人用户id
    private String lnHfUserId; //代偿人代偿时 传递借款人恒丰客户号

    public String getLnHfUserId() {
        return lnHfUserId;
    }

    public void setLnHfUserId(String lnHfUserId) {
        this.lnHfUserId = lnHfUserId;
    }

    public PartnerEnum getPartnerEnum() {
        return partnerEnum;
    }

    public void setPartnerEnum(PartnerEnum partnerEnum) {
        this.partnerEnum = partnerEnum;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Double getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(Double repayFee) {
        this.repayFee = repayFee;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

	public Integer getBsUserId() {
		return bsUserId;
	}

	public void setBsUserId(Integer bsUserId) {
		this.bsUserId = bsUserId;
	}
}
