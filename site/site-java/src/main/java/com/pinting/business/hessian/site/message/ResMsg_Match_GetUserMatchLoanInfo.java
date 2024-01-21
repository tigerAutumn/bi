package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by shh on 2016/9/19 21:32.
 */
public class ResMsg_Match_GetUserMatchLoanInfo extends ResMsg {

    private static final long serialVersionUID = -4734667758367578288L;

    /* 出借受让金额 */
    private Double approveAmount;

    /* 总期数 */
    private Integer period;

    /* 借款年化利率 */
    private Double productRate;

    /* 起息日 */
    private Date interestBeginDate;

    /* 本息 */
    private Double planTotal;

    /* 计划还款日期 */
    private Date planDate;

    /* 产品年化利率 */
    private Double regdProductRate;

    /* 借款人姓名 */
    private String borrowerName;

    /* 借款人手机号 */
    private String mobile;

    /* 借款人身份证号 */
    private String idCard;

    /* 站岗户编号 */
    private Integer authAccountId;
    
    /* 产品ID */
    private Integer productId;

    /* 最后一次还款的时间 */
    private  Date lastPlanDate;

    /* 借款用途 */
    private String purpose;

    /* 借款本金数额 */
    private Double totalAmount;

    /* 理财人id */
    private Integer bsUserId;

    /* 出借受让日期 */
    private Date loanTime;

    /* regd户子账户id */
    private Integer regdId;

    /* 借款用户编号 */
    private Integer lnUserId;

    /* 借款编号 */
    private Integer loanId;

    /*赞分期新旧协议时间区分标志*/
    private boolean zanAgreementDate;

    public Double getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(Double approveAmount) {
        this.approveAmount = approveAmount;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
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

    public String getBorrowerName() {
        return borrowerName;
    }

    public void setBorrowerName(String borrowerName) {
        this.borrowerName = borrowerName;
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

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Integer getRegdId() {
        return regdId;
    }

    public void setRegdId(Integer regdId) {
        this.regdId = regdId;
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

    public boolean isZanAgreementDate() {
        return zanAgreementDate;
    }

    public void setZanAgreementDate(boolean zanAgreementDate) {
        this.zanAgreementDate = zanAgreementDate;
    }
}
