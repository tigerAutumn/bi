package com.pinting.business.model.vo;

/**
 * 分期产品，定期产品代偿协议 债转信息VO
 * Created by zousheng on 2018/07/05 11:11.
 */
public class CompensateTransfersPdfVO {
    private Double transferCreditorAmount; // 代偿金额（协议本金 + 协议利息 + 借款服务费）
    private Integer serialId; // 期数
    private Double agreementSumAmount; // 代偿协议本息
    private Double loanServiceFee; // 借款服务费
    private String lateRepayDate; // 代偿时间

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Double getTransferCreditorAmount() {
        return transferCreditorAmount;
    }

    public void setTransferCreditorAmount(Double transferCreditorAmount) {
        this.transferCreditorAmount = transferCreditorAmount;
    }

    public String getLateRepayDate() {
        return lateRepayDate;
    }

    public void setLateRepayDate(String lateRepayDate) {
        this.lateRepayDate = lateRepayDate;
    }

    public Double getAgreementSumAmount() {
        return agreementSumAmount;
    }

    public void setAgreementSumAmount(Double agreementSumAmount) {
        this.agreementSumAmount = agreementSumAmount;
    }

    public Double getLoanServiceFee() {
        return loanServiceFee;
    }

    public void setLoanServiceFee(Double loanServiceFee) {
        this.loanServiceFee = loanServiceFee;
    }
}
