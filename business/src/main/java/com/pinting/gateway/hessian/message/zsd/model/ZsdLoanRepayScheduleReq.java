package com.pinting.gateway.hessian.message.zsd.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 借款申请服务
 * @author SHENGUOPING
 * @date  2017年8月30日 下午4:06:41
 */
public class ZsdLoanRepayScheduleReq implements Serializable {

	private static final long serialVersionUID = 4752394276516989917L;

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
     * 平台服务费
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
     */
    private Long collectionChannelFee;

    /**
     * 其他
     * 单位：分
     */
    private Long other;
    
    /**
     * 优惠金额
     * 单位：分
     */
    private Long promote;
    
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
	
}
