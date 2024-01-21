package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2016/8/29
 * Description: 委托计划债权关系VO
 */
public class LnLoanRelationVO {

    /* 借贷关系表ID */
    private Integer id;

    /* 借款人 */
    private String borrowerName;

    /* 出借受让金额 */
    private Double approveAmount;

    /* 应收总利息 */
    private Double planTotalInterest;

    /* 已收本金 */
    private Double realPrincipal;

    /* 已收本息 */
    private Double realTotal;

    /* 总期数 */
    private Integer period;

    /* 已还期数 */
    private Integer repayedPeriodCount;

    /* 出借受让日期 */
    private Date loanTime;

    /* 最近还款日期 */
    private Date latestRepayTime;

    /* 还款状态 */
    private String status;

    /* 借款人手机号码 */
    private String mobile;

    /* 借款人身份证号 */
    private String idCard;

    /* 借款年化利率 */
    private Double productRate;

    /* 起息日 */
    private Date interestBeginDate;

    /* 计划总金额 */
    private Double planTotal;

    /* 计划还款日期 */
    private Date planDate;

    /* 投资产品年化利率 */
    private Double regdProductRate;

    /* 站岗户编号 */
    private Integer authAccountId;
    
    /* 产品ID */
    private Integer productId;

    /* 最后一次还款的时间 */
    private  Date lastPlanDate;
    
    /* 债权转让个数 */
    private Integer transCount;

    /* 借款用途 */
    private String purpose;

    /* 借款本金数额 */
    private Double totalAmount;

    /* 理财人id */
    private Integer bsUserId;

    /* regd户子账户id */
    private Integer regdId;

    /* 理财人姓名 */
    private String userName;

    /* 理财人手机号码 */
    private String userMobile;

    /* 借款用户编号 */
    private Integer lnUserId;

    /* 借款id */
    private Integer loanId;
    

    /* 借款协议利率 */
    private Double agreementRate;

    /* 初始本金 */
    private Double initAmount;

    public Integer getTransCount() {
		return transCount;
	}

	public void setTransCount(Integer transCount) {
		this.transCount = transCount;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public Double getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(Double approveAmount) {
        this.approveAmount = approveAmount;
    }

    public Double getPlanTotalInterest() {
        return planTotalInterest;
    }

    public void setPlanTotalInterest(Double planTotalInterest) {
        this.planTotalInterest = planTotalInterest;
    }

    public Double getRealPrincipal() {
        return realPrincipal;
    }

    public void setRealPrincipal(Double realPrincipal) {
        this.realPrincipal = realPrincipal;
    }

    public Double getRealTotal() {
        return realTotal;
    }

    public void setRealTotal(Double realTotal) {
        this.realTotal = realTotal;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getRepayedPeriodCount() {
        return repayedPeriodCount;
    }

    public void setRepayedPeriodCount(Integer repayedPeriodCount) {
        this.repayedPeriodCount = repayedPeriodCount;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Date getLatestRepayTime() {
        return latestRepayTime;
    }

    public void setLatestRepayTime(Date latestRepayTime) {
        this.latestRepayTime = latestRepayTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Double getProductRate() {
        return productRate;
    }

    public void setProductRate(Double productRate) {
        this.productRate = productRate;
    }

	public Date getInterestBeginDate() {
        return interestBeginDate;
    }

    public void setInterestBeginDate(Date interestBeginDate) {
        this.interestBeginDate = interestBeginDate;
    }

    public Double getPlanTotal() {
        return planTotal;
    }

    public void setPlanTotal(Double planTotal) {
        this.planTotal = planTotal;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Double getRegdProductRate() {
        return regdProductRate;
    }

    public void setRegdProductRate(Double regdProductRate) {
        this.regdProductRate = regdProductRate;
    }

    public Integer getAuthAccountId() {
        return authAccountId;
    }

    public void setAuthAccountId(Integer authAccountId) {
        this.authAccountId = authAccountId;
    }

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

    public Date getLastPlanDate() {
        return lastPlanDate;
    }

    public void setLastPlanDate(Date lastPlanDate) {
        this.lastPlanDate = lastPlanDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getBsUserId() {
        return bsUserId;
    }

    public void setBsUserId(Integer bsUserId) {
        this.bsUserId = bsUserId;
    }

    public Integer getRegdId() {
        return regdId;
    }

    public void setRegdId(Integer regdId) {
        this.regdId = regdId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

	public Double getAgreementRate() {
		return agreementRate;
	}

	public void setAgreementRate(Double agreementRate) {
		this.agreementRate = agreementRate;
	}

	public Double getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(Double initAmount) {
		this.initAmount = initAmount;
	}
    
    
}
