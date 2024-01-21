package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.loan.model.RepaySchedule;

import java.util.List;

public class G2BReqMsg_Loan_Loan extends ReqMsg {

    private static final long serialVersionUID = -7526316450006915747L;
    /**
     * 借款订单号
     */
    private String orderNo;

    /**
     * 业务标识
     */
    private String businessType;

    /**
     * 用户编号
     */
    private String userId;

    /**
     * 借款编号
     */
    private String loanId;

    /**
     * 借款金额
     */
    private String loanAmount;

    /**
     * 借款期限
     */
    private String loanTerm;

    /**
     * 绑卡编号
     */
    private String bindId;

    /**
     * 借款标的名称
     */
    private String subjectName;

    /**
     * 借款用途
     */
    private String purpose;

    /**
     * 借款申请时间
     */
    private String loanApplyTime;

    /**
     * 授信金额
     */
    private String creditAmount;

    /**
     * 已借款金额
     */
    private String loanedAmount;

    /**
     * 信用等级
     */
    private String creditLevel;

    /**
     * 信用积分
     */
    private String creditScore;

    /**
     * 借款次数
     */
    private String loanTimes;

    /**
     * 违约次数
     */
    private String breakTimes;

    /**
     * 最长违约天数
     */
    private String breakMaxDays;

    /**
     * 计费规则编码
     */
    private String chargeRule;

    /**
     * 还款计划列表
     */
    private List<RepaySchedule> repaySchedule;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getLoanApplyTime() {
        return loanApplyTime;
    }

    public void setLoanApplyTime(String loanApplyTime) {
        this.loanApplyTime = loanApplyTime;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(String loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public String getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(String creditScore) {
        this.creditScore = creditScore;
    }

    public String getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(String loanTimes) {
        this.loanTimes = loanTimes;
    }

    public String getBreakTimes() {
        return breakTimes;
    }

    public void setBreakTimes(String breakTimes) {
        this.breakTimes = breakTimes;
    }

    public String getBreakMaxDays() {
        return breakMaxDays;
    }

    public void setBreakMaxDays(String breakMaxDays) {
        this.breakMaxDays = breakMaxDays;
    }

    public String getChargeRule() {
        return chargeRule;
    }

    public void setChargeRule(String chargeRule) {
        this.chargeRule = chargeRule;
    }

    public List<RepaySchedule> getRepaySchedule() {
        return repaySchedule;
    }

    public void setRepaySchedule(List<RepaySchedule> repaySchedule) {
        this.repaySchedule = repaySchedule;
    }

    public String getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(String loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(String loanTerm) {
        this.loanTerm = loanTerm;
    }
}
