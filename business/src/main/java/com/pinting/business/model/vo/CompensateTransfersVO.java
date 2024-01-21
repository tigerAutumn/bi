package com.pinting.business.model.vo;

/**
 * 代偿协议 债权转让列表VO
 * Created by shh on 2017/3/8 11:11.
 */
public class CompensateTransfersVO {

    private Double approveAmount; //借款金额

    private Double agreementRate; //借款利率

    private Integer period; //借款期限

    private Double planAmount; //最后一期还款本金

    private Double transferCreditorAmount; //债权转让金额

    private Double debtServiceFee; //单笔债转的服务费

    private String yunDaiSelfUserName; //云贷自主放款债权受让人名字

    private String yunDaiSelfIdCard; //云贷自主放款债权受让人身份证号

    private String loanAgreementNumber; //借款协议编号

    private Integer noBillingPeriod; //未还账单期限

    private String lateRepayDate; //代偿成功的时间

    private Integer lnLoanRelationId; //借贷关系id

    private String userName; //理财人姓名(出借人)

    private String idCard; //理财人身份证号

    private String loanUserName; //借款人姓名

    private String loanIdCard; //借款人身份证号

    private Double beforeAmount; //变动前金额(未还本金)

    private Double initAmount; //初始化金额

    private Double noticeTransferCreditorAmount; //债转通知书中的债权转让金额
    
    private Double agreementAmount; //未还本金*借款利率*未还账单期限/365+本金

    private Integer serialId; //还款期次/代偿期数

    private Integer sevenPeriod; //7贷借款期限

    private Integer sevenCompensateCount; //债权转让通知书-统计7贷代偿人 代偿的笔数

    private String userMobile; //理财人手机号

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

    public Double getPlanAmount() {
        return planAmount;
    }

    public void setPlanAmount(Double planAmount) {
        this.planAmount = planAmount;
    }

    public Double getTransferCreditorAmount() {
        return transferCreditorAmount;
    }

    public void setTransferCreditorAmount(Double transferCreditorAmount) {
        this.transferCreditorAmount = transferCreditorAmount;
    }

    public Double getDebtServiceFee() {
        return debtServiceFee;
    }

    public void setDebtServiceFee(Double debtServiceFee) {
        this.debtServiceFee = debtServiceFee;
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

    public String getLoanAgreementNumber() {
        return loanAgreementNumber;
    }

    public void setLoanAgreementNumber(String loanAgreementNumber) {
        this.loanAgreementNumber = loanAgreementNumber;
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

    public Double getInitAmount() {
        return initAmount;
    }

    public void setInitAmount(Double initAmount) {
        this.initAmount = initAmount;
    }

    public Double getNoticeTransferCreditorAmount() {
        return noticeTransferCreditorAmount;
    }

    public void setNoticeTransferCreditorAmount(Double noticeTransferCreditorAmount) {
        this.noticeTransferCreditorAmount = noticeTransferCreditorAmount;
    }

	public Double getAgreementAmount() {
		return agreementAmount;
	}

	public void setAgreementAmount(Double agreementAmount) {
		this.agreementAmount = agreementAmount;
	}

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getSevenPeriod() {
        return sevenPeriod;
    }

    public void setSevenPeriod(Integer sevenPeriod) {
        this.sevenPeriod = sevenPeriod;
    }

    public Integer getSevenCompensateCount() {
        return sevenCompensateCount;
    }

    public void setSevenCompensateCount(Integer sevenCompensateCount) {
        this.sevenCompensateCount = sevenCompensateCount;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }
}
