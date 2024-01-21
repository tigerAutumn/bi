package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

/**
 * 存管港湾产品债权转让协议 出参
 * Created by zousheng on 2018/05/09 13:23.
 */
public class ResMsg_Match_GetCustodyClaimsTransferInfoPdf extends ResMsg {

    private static final long serialVersionUID = 1L;

    private Integer inSubAccountId; //受让人子账户编号

    private Double amount; //转让金额

    private Double inAmount; //本次转让债权价值(受让人出资金额)

    private String outUserName; //出让人姓名

    private String inUserName; //受让人姓名

    private String outUserIdCardNo; //出让人身份证

    private String inUserIdCardNo; //受让人身份证

    private String borrowUserName; //借款人姓名

    private String borrowUserIdCardNo; //借款人身份证

    private Integer term; //还款期限（月）

    private Date transferTime; //转让时间

    private Double approveAmount; //借款批准金额

    private Double agreementRate; //借款协议利率

    private Double leftAmount; //未还本金

    private String yunDaiSelfUserName; //云贷自主放款债权受让人名字

    private String yunDaiSelfIdCard; //云贷自主放款债权受让人身份证号

    private String transMark; //债转标记

    private String transStatus; //债转状态

    // 业务标识
    private String partnerBusinessFlag;

    public Integer getInSubAccountId() {
        return inSubAccountId;
    }

    public void setInSubAccountId(Integer inSubAccountId) {
        this.inSubAccountId = inSubAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
    }

    public String getOutUserName() {
        return outUserName;
    }

    public void setOutUserName(String outUserName) {
        this.outUserName = outUserName;
    }

    public String getInUserName() {
        return inUserName;
    }

    public void setInUserName(String inUserName) {
        this.inUserName = inUserName;
    }

    public String getOutUserIdCardNo() {
        return outUserIdCardNo;
    }

    public void setOutUserIdCardNo(String outUserIdCardNo) {
        this.outUserIdCardNo = outUserIdCardNo;
    }

    public String getInUserIdCardNo() {
        return inUserIdCardNo;
    }

    public void setInUserIdCardNo(String inUserIdCardNo) {
        this.inUserIdCardNo = inUserIdCardNo;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getBorrowUserIdCardNo() {
        return borrowUserIdCardNo;
    }

    public void setBorrowUserIdCardNo(String borrowUserIdCardNo) {
        this.borrowUserIdCardNo = borrowUserIdCardNo;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

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

    public Double getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Double leftAmount) {
        this.leftAmount = leftAmount;
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

    public String getTransMark() {
        return transMark;
    }

    public void setTransMark(String transMark) {
        this.transMark = transMark;
    }

    public String getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(String transStatus) {
        this.transStatus = transStatus;
    }

    public String getPartnerBusinessFlag() {
        return partnerBusinessFlag;
    }

    public void setPartnerBusinessFlag(String partnerBusinessFlag) {
        this.partnerBusinessFlag = partnerBusinessFlag;
    }
}
