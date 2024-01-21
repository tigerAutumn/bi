package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 代偿-债权转让协议 出参
 * Created by shh on 2017/3/8 17:20.
 */
public class ResMsg_Match_GetCompensateTransferInfoRenew extends ResMsg {

    private static final long serialVersionUID = -7771201786531978508L;

    private Double approveAmount; //借款金额

    private Double agreementRate; //借款利率

    private Integer period; //借款期限

    private Double transferCreditorAmount; //债权转让金额

    private String yunDaiSelfUserName; //云贷自主放款债权受让人名字

    private String yunDaiSelfIdCard; //云贷自主放款债权受让人身份证号

    private Integer noBillingPeriod; //未还账单期限

    private String lateRepayDate; //代偿成功的时间

    private Integer lnLoanRelationId; //借贷关系id

    private String userName; //理财人姓名(出借人)

    private String idCard; //理财人身份证号

    private String loanUserName; //借款人姓名

    private String loanIdCard; //借款人身份证号

    private Double beforeAmount; //变动前金额(未还本金)

    private Integer sevenPeriod; //7贷借款期限

    private String userMobile; //理财人手机号

    private String yunDaiSelfMobile; //云贷自主放款债权受让人手机号

    public Double getApproveAmount() {
        return approveAmount;
    }

    public void setApproveAmount(Double approveAmount) {
        this.approveAmount = approveAmount;
    }

    public Double getAgreementRate() {
        return agreementRate;
    }

    public void setAgreementRate(Double agreementRate) {
        this.agreementRate = agreementRate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getTransferCreditorAmount() {
        return transferCreditorAmount;
    }

    public void setTransferCreditorAmount(Double transferCreditorAmount) {
        this.transferCreditorAmount = transferCreditorAmount;
    }

    public String getYunDaiSelfUserName() {
        return yunDaiSelfUserName;
    }

    public void setYunDaiSelfUserName(String yunDaiSelfUserName) {
        this.yunDaiSelfUserName = yunDaiSelfUserName;
    }

    public String getYunDaiSelfIdCard() {
        return yunDaiSelfIdCard;
    }

    public void setYunDaiSelfIdCard(String yunDaiSelfIdCard) {
        this.yunDaiSelfIdCard = yunDaiSelfIdCard;
    }

    public Integer getNoBillingPeriod() {
        return noBillingPeriod;
    }

    public void setNoBillingPeriod(Integer noBillingPeriod) {
        this.noBillingPeriod = noBillingPeriod;
    }

    public String getLateRepayDate() {
        return lateRepayDate;
    }

    public void setLateRepayDate(String lateRepayDate) {
        this.lateRepayDate = lateRepayDate;
    }

    public Integer getLnLoanRelationId() {
        return lnLoanRelationId;
    }

    public void setLnLoanRelationId(Integer lnLoanRelationId) {
        this.lnLoanRelationId = lnLoanRelationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLoanUserName() {
        return loanUserName;
    }

    public void setLoanUserName(String loanUserName) {
        this.loanUserName = loanUserName;
    }

    public String getLoanIdCard() {
        return loanIdCard;
    }

    public void setLoanIdCard(String loanIdCard) {
        this.loanIdCard = loanIdCard;
    }

    public Double getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(Double beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public Integer getSevenPeriod() {
        return sevenPeriod;
    }

    public void setSevenPeriod(Integer sevenPeriod) {
        this.sevenPeriod = sevenPeriod;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getYunDaiSelfMobile() {
        return yunDaiSelfMobile;
    }

    public void setYunDaiSelfMobile(String yunDaiSelfMobile) {
        this.yunDaiSelfMobile = yunDaiSelfMobile;
    }
}
