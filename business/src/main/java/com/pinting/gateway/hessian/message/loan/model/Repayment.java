package com.pinting.gateway.hessian.message.loan.model;

import java.io.Serializable;

/**
 * Created by 剑钊 on 2016/8/10.
 * 还款计划
 */
public class Repayment implements Serializable{

    /**
     * 还款编号
     */
    private String repayId;

    /**
     * 还款类型
     * ALL:全部还款
     * PART:部分还款
     */
    private String repayType;

    /**
     * 还款总金额
     */
    private Long total;

    /**
     * 本金
     * 单位：分
     */
    private Long principal;

    /**
     * 利息
     * 单位：分
     */
    private Long interest;

    /**
     * 滞纳金
     */
    private Long lateFee;

    /**
     * 手续费
     * 单位：分
     */
    private Long serviceFee;

    /**
     * 监管费
     * 单位：分
     */
    private Long superviseFee;

    /**
     * 信息服务费
     */
    private Long infoServiceFee;

    /**
     * 账户服务费
     */
    private Long accountManageFee;

    /**
     * 保费
     * 单位：分
     */
    private Long premium;

    /**
     * 其他
     * 单位：分
     */
    private Long other;
    
    

    /**
     * 平台服务费
     * 单位：分
     */
    private Long platformServiceFee;

    /**
     * 信息认证费
     */
    private Long infoCertifiedFee;

    /**
     * 风控服务费
     */
    private Long riskManageServiceFee;

    /**
     * 代收通道费
     * 单位：分
     */
    private Long collectionChannelFee;

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPrincipal() {
        return principal;
    }

    public void setPrincipal(Long principal) {
        this.principal = principal;
    }

    public Long getInterest() {
        return interest;
    }

    public void setInterest(Long interest) {
        this.interest = interest;
    }

    public Long getLateFee() {
        return lateFee;
    }

    public void setLateFee(Long lateFee) {
        this.lateFee = lateFee;
    }

    public Long getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Long serviceFee) {
        this.serviceFee = serviceFee;
    }

    public Long getSuperviseFee() {
        return superviseFee;
    }

    public void setSuperviseFee(Long superviseFee) {
        this.superviseFee = superviseFee;
    }

    public Long getInfoServiceFee() {
        return infoServiceFee;
    }

    public void setInfoServiceFee(Long infoServiceFee) {
        this.infoServiceFee = infoServiceFee;
    }

    public Long getAccountManageFee() {
        return accountManageFee;
    }

    public void setAccountManageFee(Long accountManageFee) {
        this.accountManageFee = accountManageFee;
    }

    public Long getPremium() {
        return premium;
    }

    public void setPremium(Long premium) {
        this.premium = premium;
    }

    public Long getOther() {
        return other;
    }

    public void setOther(Long other) {
        this.other = other;
    }

	public Long getPlatformServiceFee() {
		return platformServiceFee;
	}

	public void setPlatformServiceFee(Long platformServiceFee) {
		this.platformServiceFee = platformServiceFee;
	}

	public Long getInfoCertifiedFee() {
		return infoCertifiedFee;
	}

	public void setInfoCertifiedFee(Long infoCertifiedFee) {
		this.infoCertifiedFee = infoCertifiedFee;
	}

	public Long getRiskManageServiceFee() {
		return riskManageServiceFee;
	}

	public void setRiskManageServiceFee(Long riskManageServiceFee) {
		this.riskManageServiceFee = riskManageServiceFee;
	}

	public Long getCollectionChannelFee() {
		return collectionChannelFee;
	}

	public void setCollectionChannelFee(Long collectionChannelFee) {
		this.collectionChannelFee = collectionChannelFee;
	}
    
}
