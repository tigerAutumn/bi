package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 日常业务-借款账单管理VO
 *
 * @author shh
 * @date 2018/7/9 9:44
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class LoanBillVO extends PageInfoObject {

    /* 资产方 */
    private String partnerCode;

    /* 借款产品 */
    private String partnerBusinessFlag;

    /* 账单日 */
    private Date planDate;

    /* 借款编号 */
    private String partnerLoanId;

    /* 账单编号 */
    private String partnerRepayId;

    /* 账单状态 */
    private String status;

    /* 账单本金 */
    private Double schedulePrincipalPlanAmount;

    /* 账单利息 */
    private Double scheduleInterestPlanAmount;

    /* 还款类型 */
    private String repayType;

    /* 还款时间 */
    private Date doneTime;

    /* 还款总额 */
    private Double repayTotal;

    /* 还款本金 */
    private Double repayPrincipalDoneAmount;

    /* 还款利息 */
    private Double repayInterestDoneAmount;

    /* 账单日开始时间 --查询条件*/
    private String planDateStart;

    /* 账单日结束时间 -- 查询条件 */
    private String planDateEnd;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerBusinessFlag() {
        return partnerBusinessFlag;
    }

    public void setPartnerBusinessFlag(String partnerBusinessFlag) {
        this.partnerBusinessFlag = partnerBusinessFlag;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public String getPartnerRepayId() {
        return partnerRepayId;
    }

    public void setPartnerRepayId(String partnerRepayId) {
        this.partnerRepayId = partnerRepayId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getSchedulePrincipalPlanAmount() {
        return schedulePrincipalPlanAmount;
    }

    public void setSchedulePrincipalPlanAmount(Double schedulePrincipalPlanAmount) {
        this.schedulePrincipalPlanAmount = schedulePrincipalPlanAmount;
    }

    public Double getScheduleInterestPlanAmount() {
        return scheduleInterestPlanAmount;
    }

    public void setScheduleInterestPlanAmount(Double scheduleInterestPlanAmount) {
        this.scheduleInterestPlanAmount = scheduleInterestPlanAmount;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Double getRepayPrincipalDoneAmount() {
        return repayPrincipalDoneAmount;
    }

    public void setRepayPrincipalDoneAmount(Double repayPrincipalDoneAmount) {
        this.repayPrincipalDoneAmount = repayPrincipalDoneAmount;
    }

    public Double getRepayInterestDoneAmount() {
        return repayInterestDoneAmount;
    }

    public void setRepayInterestDoneAmount(Double repayInterestDoneAmount) {
        this.repayInterestDoneAmount = repayInterestDoneAmount;
    }

    public String getPlanDateStart() {
        return planDateStart;
    }

    public void setPlanDateStart(String planDateStart) {
        this.planDateStart = planDateStart;
    }

    public String getPlanDateEnd() {
        return planDateEnd;
    }

    public void setPlanDateEnd(String planDateEnd) {
        this.planDateEnd = planDateEnd;
    }

	public Double getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(Double repayTotal) {
		this.repayTotal = repayTotal;
	}
}
