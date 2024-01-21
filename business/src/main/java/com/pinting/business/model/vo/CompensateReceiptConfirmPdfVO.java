package com.pinting.business.model.vo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 分期产品，定期产品代偿协议 收款确认函，通知书VO
 * Created by zousheng on 2018/07/05 11:11.
 */
public class CompensateReceiptConfirmPdfVO {
    private Integer loanId; // 借款ID
    private String loanUserName; // 借款人姓名
    private String loanIdCard; // 借款人身份证号
    private String loanAgreementNumber; // 借款协议号
    private Double approveAmount; // 借款金额
    private Double agreementRate; // 借款协议利率
    private Integer period; // 借款期数
    private Double transferCreditorTotalAmount; // 代偿合计金额
    private ArrayList<HashMap<String, Object>> compemsateInfos;

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

    public String getLoanAgreementNumber() {
        return loanAgreementNumber;
    }

    public void setLoanAgreementNumber(String loanAgreementNumber) {
        this.loanAgreementNumber = loanAgreementNumber;
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public ArrayList<HashMap<String, Object>> getCompemsateInfos() {
        return compemsateInfos;
    }

    public void setCompemsateInfos(ArrayList<HashMap<String, Object>> compemsateInfos) {
        this.compemsateInfos = compemsateInfos;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Double getTransferCreditorTotalAmount() {
        return transferCreditorTotalAmount;
    }

    public void setTransferCreditorTotalAmount(Double transferCreditorTotalAmount) {
        this.transferCreditorTotalAmount = transferCreditorTotalAmount;
    }
}


