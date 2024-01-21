package com.pinting.business.model.vo;

/**
 * 币港湾实验室功能-借款用户还款情况VO
 *
 * @author shh
 * @date 2018/6/1 13:33
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class LoanRepayVO extends PageInfoObject {

    /* 资产方 */
    private String partnerCode;

    /* 借款用户编号 */
    private Integer loanUserId;

    /* 借款用户姓名 */
    private String loanUserName;

    /* 借款次数 */
    private Integer loanCount;

    /* 提前还款次数 */
    private Integer advancedRepayCount;

    /* 按期还款次数 */
    private Integer repayCount;

    /* 本金逾期次数 */
    private Integer overduePrincipalCompensate;

    /* 利息逾期次数 */
    private Integer overdueInterestCompensate;

    /* 列表页查询条件 */

    /* 借款次数起始值 */
    private Integer loanCountStart;

    /* 借款次数结束值 */
    private Integer loanCountEnd;

    /* 提前还款次数起始值 */
    private Integer advancedRepayCountStart;

    /* 提前还款次数结束值 */
    private Integer advancedRepayCountEnd;

    /* 按期还款次数起始值 */
    private Integer repayCountStart;

    /* 按期还款次数结束值 */
    private Integer repayCountEnd;

    /* 本金逾期次数起始值 */
    private Integer overduePrincipalCompensateStart;

    /* 本金逾期次数结束值 */
    private Integer overduePrincipalCompensateEnd;

    /* 利息逾期次数起始值 */
    private Integer overdueInterestCompensateStart;

    /* 利息逾期次数结束值 */
    private Integer overdueInterestCompensateEnd;

    /** 排序 */
    private String orderField;

    private String orderDirection;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Integer getLoanUserId() {
        return loanUserId;
    }

    public void setLoanUserId(Integer loanUserId) {
        this.loanUserId = loanUserId;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public Integer getLoanCount() {
        return loanCount;
    }

    public void setLoanCount(Integer loanCount) {
        this.loanCount = loanCount;
    }

    public Integer getAdvancedRepayCount() {
        return advancedRepayCount;
    }

    public void setAdvancedRepayCount(Integer advancedRepayCount) {
        this.advancedRepayCount = advancedRepayCount;
    }

    public Integer getRepayCount() {
        return repayCount;
    }

    public void setRepayCount(Integer repayCount) {
        this.repayCount = repayCount;
    }

    public Integer getOverduePrincipalCompensate() {
        return overduePrincipalCompensate;
    }

    public void setOverduePrincipalCompensate(Integer overduePrincipalCompensate) {
        this.overduePrincipalCompensate = overduePrincipalCompensate;
    }

    public Integer getOverdueInterestCompensate() {
        return overdueInterestCompensate;
    }

    public void setOverdueInterestCompensate(Integer overdueInterestCompensate) {
        this.overdueInterestCompensate = overdueInterestCompensate;
    }

    public Integer getLoanCountStart() {
        return loanCountStart;
    }

    public void setLoanCountStart(Integer loanCountStart) {
        this.loanCountStart = loanCountStart;
    }

    public Integer getLoanCountEnd() {
        return loanCountEnd;
    }

    public void setLoanCountEnd(Integer loanCountEnd) {
        this.loanCountEnd = loanCountEnd;
    }

    public Integer getAdvancedRepayCountStart() {
        return advancedRepayCountStart;
    }

    public void setAdvancedRepayCountStart(Integer advancedRepayCountStart) {
        this.advancedRepayCountStart = advancedRepayCountStart;
    }

    public Integer getAdvancedRepayCountEnd() {
        return advancedRepayCountEnd;
    }

    public void setAdvancedRepayCountEnd(Integer advancedRepayCountEnd) {
        this.advancedRepayCountEnd = advancedRepayCountEnd;
    }

    public Integer getRepayCountStart() {
        return repayCountStart;
    }

    public void setRepayCountStart(Integer repayCountStart) {
        this.repayCountStart = repayCountStart;
    }

    public Integer getRepayCountEnd() {
        return repayCountEnd;
    }

    public void setRepayCountEnd(Integer repayCountEnd) {
        this.repayCountEnd = repayCountEnd;
    }

    public Integer getOverduePrincipalCompensateStart() {
        return overduePrincipalCompensateStart;
    }

    public void setOverduePrincipalCompensateStart(Integer overduePrincipalCompensateStart) {
        this.overduePrincipalCompensateStart = overduePrincipalCompensateStart;
    }

    public Integer getOverduePrincipalCompensateEnd() {
        return overduePrincipalCompensateEnd;
    }

    public void setOverduePrincipalCompensateEnd(Integer overduePrincipalCompensateEnd) {
        this.overduePrincipalCompensateEnd = overduePrincipalCompensateEnd;
    }

    public Integer getOverdueInterestCompensateStart() {
        return overdueInterestCompensateStart;
    }

    public void setOverdueInterestCompensateStart(Integer overdueInterestCompensateStart) {
        this.overdueInterestCompensateStart = overdueInterestCompensateStart;
    }

    public Integer getOverdueInterestCompensateEnd() {
        return overdueInterestCompensateEnd;
    }

    public void setOverdueInterestCompensateEnd(Integer overdueInterestCompensateEnd) {
        this.overdueInterestCompensateEnd = overdueInterestCompensateEnd;
    }

    @Override
    public String getOrderField() {
        return orderField;
    }

    @Override
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    @Override
    public String getOrderDirection() {
        return orderDirection;
    }

    @Override
    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }
}
