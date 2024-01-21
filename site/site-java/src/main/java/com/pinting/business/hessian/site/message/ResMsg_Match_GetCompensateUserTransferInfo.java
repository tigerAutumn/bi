package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 代偿协议债权转让通知书 出参
 * Created by shh on 2017/3/8 14:39.
 */
public class ResMsg_Match_GetCompensateUserTransferInfo extends ResMsg {

    private static final long serialVersionUID = 5166098952968431673L;

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

    private String loanUserName; //借款人姓名

    private String loanIdCard; //借款人身份证号

    private ArrayList<HashMap<String, Object>> dataGrid; //债权转让列表

    private Integer dataSize;

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

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }

    public Integer getDataSize() {
        return dataSize;
    }

    public void setDataSize(Integer dataSize) {
        this.dataSize = dataSize;
    }
}
