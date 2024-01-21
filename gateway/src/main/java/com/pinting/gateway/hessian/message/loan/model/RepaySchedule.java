package com.pinting.gateway.hessian.message.loan.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 剑钊 on 2016/8/10.
 */
public class RepaySchedule implements Serializable{


    private static final long serialVersionUID = 1225285522453081581L;
    /**
     * 还款编号（还款的唯一编号）
     */
    private String repayId;

    /**
     * 还款日期
     */
    private Date repayDate;

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
     * 优惠金额
     */
    private Long promote;

    /**
     * 信息服务费
     */
    private Long infoServiceFee;

    /**
     * 账户管理费
     */
    private Long accountManageFee;
    
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

    public Date getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(Date repayDate) {
        this.repayDate = repayDate;
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

    public Long getPromote() {
        return promote;
    }

    public void setPromote(Long promote) {
        this.promote = promote;
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
