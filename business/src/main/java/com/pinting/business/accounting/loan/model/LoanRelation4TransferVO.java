package com.pinting.business.accounting.loan.model;

import java.util.Date;

import com.pinting.business.model.LnLoanRelation;

/**
 * 等待转让的债权数据
 * @author bianyatian
 * @2016-12-28 下午2:35:35
 */
public class LoanRelation4TransferVO extends LnLoanRelation {

	private Date relationBeginDate; // 出让人  获取债权的时间
	
	private Double baseRate; // 基准利率

    private Double maxRate; // 最高利率
    
    private Date interestBeginDate; //理财起息日
    
    private Double lastPayInterest; // 上次接收债转时支付利息
    
    private Date lastPayInterestDate; //上次只还利息的时间
    
    private Date lastFinishInterestDate; //理财人自然回款日（最后一次计息日）
    
    private Double agreementRate; //借款协议利率
    
    private String hfUserId; //客户号

    private String partnerCode; //借款资产端编码
    
    private Date lastRepayPlanDate; //上次还款计划还款日期
    
    private Date thisRepayPlanDate; //当期账单计划还款日期
    
    private Integer thisRepayPlanSerialId;//当期账单计划还款期数
    
    private Date loanDate; //借款成功日
    
    private Integer loanPeriod; //借款期数
    
    private String partnerBusinessFlag; //合作方业务标识
    
    private Double bgwSettleRate;		//结算利率
    
    private Double approveAmount;		//借款总金额
    
	public Double getBaseRate() {
		return baseRate;
	}

	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}

	public Double getMaxRate() {
		return maxRate;
	}

	public void setMaxRate(Double maxRate) {
		this.maxRate = maxRate;
	}

	public Date getInterestBeginDate() {
		return interestBeginDate;
	}

	public void setInterestBeginDate(Date interestBeginDate) {
		this.interestBeginDate = interestBeginDate;
	}

	public Double getLastPayInterest() {
		return lastPayInterest;
	}

	public void setLastPayInterest(Double lastPayInterest) {
		this.lastPayInterest = lastPayInterest;
	}

	public Date getLastPayInterestDate() {
		return lastPayInterestDate;
	}

	public void setLastPayInterestDate(Date lastPayInterestDate) {
		this.lastPayInterestDate = lastPayInterestDate;
	}

	public Date getRelationBeginDate() {
		return relationBeginDate;
	}

	public void setRelationBeginDate(Date relationBeginDate) {
		this.relationBeginDate = relationBeginDate;
	}

	public Date getLastFinishInterestDate() {
		return lastFinishInterestDate;
	}

	public void setLastFinishInterestDate(Date lastFinishInterestDate) {
		this.lastFinishInterestDate = lastFinishInterestDate;
	}

	public Double getAgreementRate() {
		return agreementRate;
	}

	public void setAgreementRate(Double agreementRate) {
		this.agreementRate = agreementRate;
	}

	public String getHfUserId() {
		return hfUserId;
	}

	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Date getLastRepayPlanDate() {
		return lastRepayPlanDate;
	}

	public void setLastRepayPlanDate(Date lastRepayPlanDate) {
		this.lastRepayPlanDate = lastRepayPlanDate;
	}

	public Date getThisRepayPlanDate() {
		return thisRepayPlanDate;
	}

	public void setThisRepayPlanDate(Date thisRepayPlanDate) {
		this.thisRepayPlanDate = thisRepayPlanDate;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public Integer getLoanPeriod() {
		return loanPeriod;
	}

	public void setLoanPeriod(Integer loanPeriod) {
		this.loanPeriod = loanPeriod;
	}

	public Integer getThisRepayPlanSerialId() {
		return thisRepayPlanSerialId;
	}

	public void setThisRepayPlanSerialId(Integer thisRepayPlanSerialId) {
		this.thisRepayPlanSerialId = thisRepayPlanSerialId;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}

	public Double getBgwSettleRate() {
		return bgwSettleRate;
	}

	public void setBgwSettleRate(Double bgwSettleRate) {
		this.bgwSettleRate = bgwSettleRate;
	}

	public Double getApproveAmount() {
		return approveAmount;
	}

	public void setApproveAmount(Double approveAmount) {
		this.approveAmount = approveAmount;
	}
}
