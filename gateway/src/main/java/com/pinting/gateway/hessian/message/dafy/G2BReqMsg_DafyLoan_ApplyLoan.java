package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class G2BReqMsg_DafyLoan_ApplyLoan extends ReqMsg {
    /**
     *
     */
    private static final long serialVersionUID = 4019497953730907602L;
    /*云贷用户唯一编号*/
    @NotEmpty(message = "userId为空")
    private String userId;
    /*姓名*/
    @NotEmpty(message = "name为空")
    private String name;
    /*身份证*/
    @NotEmpty(message = "idCard为空")
    private String idCard;
    /*预留手机号*/
    @NotEmpty(message = "mobile为空")
    private String mobile;
    /*卡号*/
    @NotEmpty(message = "bankCard为空")
    private String bankCard;
    /*银行编码*/
    @NotEmpty(message = "bankCode为空")
    private String bankCode;
    /*借款单号*/
    @NotEmpty(message = "orderNo为空")
    private String orderNo;
    /*申请时间*/
    @NotNull(message = "applyTime为空")
    private Date applyTime;
    /*业务标识*/
    @NotEmpty(message = "businessType为空")
    private String businessType;
    /*借款编号*/
    @NotEmpty(message = "loanId为空")
    private String loanId;
    /*借款金额*/
    @NotNull(message = "loanAmount为空")
    private Double loanAmount;
    /*借款手续费*/
    @NotNull(message = "loanFee为空")
    private Double loanFee;
    /*借款期数*/
    @NotNull(message = "loanTerm为空")
    private Integer loanTerm;
    /*借款利率    100 = 0.01%*/
    @NotNull(message = "loanRate为空")
    private Integer loanRate;
    /*借款标的名称*/
    private String subjectName;
    /*借款用途*/
    private String purpose;
    /*授信金额*/
    private Double creditAmount;
    /*已借款金额*/
    private Double loanedAmount;
    /*信用等级*/
    private String creditLevel;
    /*信用积分*/
    private Integer creditScore;
    /*借款次数*/
    private Integer loanTimes;
    /*违约次数*/
    private Integer breakTimes;
    /*最长违约天数*/
    private Integer breakMaxDays;
    /*工作单位*/
    private String workUnit;
    /*学历*/
    private String education;
    /*婚姻*/
    private String marriage;
    /*月收入*/
    private Double monthlyIncome;
    /*借款人地址*/
    @NotEmpty(message = "address为空")
    private String address;
    /*借款人邮箱*/
    private String email;


    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Double getLoanFee() {
        return loanFee;
    }

    public void setLoanFee(Double loanFee) {
        this.loanFee = loanFee;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getLoanedAmount() {
        return loanedAmount;
    }

    public void setLoanedAmount(Double loanedAmount) {
        this.loanedAmount = loanedAmount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Integer getLoanRate() {
        return loanRate;
    }

    public void setLoanRate(Integer loanRate) {
        this.loanRate = loanRate;
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

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public Integer getLoanTimes() {
        return loanTimes;
    }

    public void setLoanTimes(Integer loanTimes) {
        this.loanTimes = loanTimes;
    }

    public Integer getBreakTimes() {
        return breakTimes;
    }

    public void setBreakTimes(Integer breakTimes) {
        this.breakTimes = breakTimes;
    }

    public Integer getBreakMaxDays() {
        return breakMaxDays;
    }

    public void setBreakMaxDays(Integer breakMaxDays) {
        this.breakMaxDays = breakMaxDays;
    }

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public Double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(Double monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
