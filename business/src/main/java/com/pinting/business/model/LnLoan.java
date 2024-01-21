package com.pinting.business.model;

import java.util.Date;

public class LnLoan {
    private Integer id; // 用户借款申请表Id

    private Integer lnUserId; // 借款用户表编号

    private Integer chargeRuleId; // 计费规则表编号

    private String partnerLoanId; // 合作方借款编号

    private Double applyAmount; // 申请金额

    private Double approveAmount; // 批准金额-以此金额为准

    private Double headFee; // 砍头息

    private Double agreementRate; // 借款协议利率

    private Double loanServiceRate; // 借款服务费率

    private Double bgwSettleRate; // 币港湾结算利率

    private String payOrderNo; // 支付订单号-币港湾提交给支付平台的订单号

    private Integer period; // 期限，计费规则里有，此处冗余出来

    private Integer periodUnit; // 期数单位  1日 2周 3月 4季 5年

    private String partnerOrderNo; // 合作方借款订单号-合作方和我方系统之间的订单号

    private String partnerBusinessFlag; // 合作方业务标识：REPAY_ANY_TIME（消费循环贷） / FIXED_INSTALLMENT（等额本息）/ FIXED_PRINCIPAL_INTEREST（等本等息）

    private String bgwBindId; // 绑卡编号-我方提供给合作方的绑卡编号

    private String subjectName; // 借款标的名称

    private String purpose; // 借款用途

    private Date applyTime; // 借款申请时间

    private Double creditAmount; // 授信金额

    private Double loanedAmount; // 已借款金额

    private String creditLevel; // 信用等级

    private Integer creditScore; // 信用积分

    private Integer loanTimes; // 借款次数

    private Integer breakTimes; // 违约次数

    private Integer breakMaxDays; // 最长违约天数

    private String address; // 借款人地址

    private String email; // 借款人邮箱

    private String status; // WAIT_CHECK待审核\r\n            CHECKED 审核通过\r\n            CHECK_NOT_PASS 审核不通过\r\n            PAYING 放款中\r\n            PAIED 放款成功\r\n            PAY_FAIL 放款失败

    private String isRepaying; // 是否还款中\r\n  AVAILABLE 可还款\r\n  REPAYING 还款中

    private String informStatus; // 借款完成后通知合作方的状态\r\n            INIT 未通知\r\n            SUCCESS 通知成功\r\n            FAIL 通知失败

    private Date loanTime; // 借款时间-借款成功的时间

    private Date interestTime; // 起息日期

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public Double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Double getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(Double approveAmount) {
        this.approveAmount = approveAmount;
    }

    public Double getHeadFee() {
        return headFee;
    }

    public void setHeadFee(Double headFee) {
        this.headFee = headFee;
    }

    public Double getAgreementRate() {
        return agreementRate;
    }

    public void setAgreementRate(Double agreementRate) {
        this.agreementRate = agreementRate;
    }

    public Double getLoanServiceRate() {
        return loanServiceRate;
    }

    public void setLoanServiceRate(Double loanServiceRate) {
        this.loanServiceRate = loanServiceRate;
    }

    public Double getBgwSettleRate() {
        return bgwSettleRate;
    }

    public void setBgwSettleRate(Double bgwSettleRate) {
        this.bgwSettleRate = bgwSettleRate;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(Integer periodUnit) {
        this.periodUnit = periodUnit;
    }

    public String getPartnerOrderNo() {
        return partnerOrderNo;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo;
    }

    public String getPartnerBusinessFlag() {
        return partnerBusinessFlag;
    }

    public void setPartnerBusinessFlag(String partnerBusinessFlag) {
        this.partnerBusinessFlag = partnerBusinessFlag;
    }

    public String getBgwBindId() {
        return bgwBindId;
    }

    public void setBgwBindId(String bgwBindId) {
        this.bgwBindId = bgwBindId;
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

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsRepaying() {
        return isRepaying;
    }

    public void setIsRepaying(String isRepaying) {
        this.isRepaying = isRepaying;
    }

    public String getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(String informStatus) {
        this.informStatus = informStatus;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Date getInterestTime() {
        return interestTime;
    }

    public void setInterestTime(Date interestTime) {
        this.interestTime = interestTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}