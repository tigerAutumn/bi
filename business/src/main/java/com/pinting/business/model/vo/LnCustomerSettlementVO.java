package com.pinting.business.model.vo;

import com.pinting.business.accounting.loan.enums.PartnerEnum;

import java.util.Date;

/**
 * Created by cyb on 2017/11/16.
 */
public class LnCustomerSettlementVO {

    private Integer id;
    // 序号
    private Integer rowNo;
    // 单据编号
    private String billNo;
    // 投资客户代码
    private String fnCustomerId;
    // 投资人
    private String fnUserName;
    // 投资人手机号
    private String mobile;
    // 融资客户名称
    private String lnUserName;
    // 融资客户代码
    private String lnCustomerId;
    // 资产方
    private String partnerCode;
    // 资产方名称
    private String partnerCodeDesc;
    // 结算本金
    private Double planPrincipal;
    // 融资客户应付利息
    private Double lnPlanInterest;
    // 应付投资客户利息
    private Double fnPlanInterest;
    // 手续费（宝付）
    private Double fee;
    // 结算日期
    private Date transTime;
    // 备注
    private String repayType;
    private Integer serialId;
    // 借款产品标识
    private String partnerBusinessFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getFnCustomerId() {
        return fnCustomerId;
    }

    public void setFnCustomerId(String fnCustomerId) {
        this.fnCustomerId = fnCustomerId;
    }

    public String getFnUserName() {
        return fnUserName;
    }

    public void setFnUserName(String fnUserName) {
        this.fnUserName = fnUserName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getLnUserName() {
        return lnUserName;
    }

    public void setLnUserName(String lnUserName) {
        this.lnUserName = lnUserName;
    }

    public String getLnCustomerId() {
        return lnCustomerId;
    }

    public void setLnCustomerId(String lnCustomerId) {
        this.lnCustomerId = lnCustomerId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Double getPlanPrincipal() {
        return planPrincipal;
    }

    public void setPlanPrincipal(Double planPrincipal) {
        this.planPrincipal = planPrincipal;
    }

    public Double getLnPlanInterest() {
        return lnPlanInterest;
    }

    public void setLnPlanInterest(Double lnPlanInterest) {
        this.lnPlanInterest = lnPlanInterest;
    }

    public Double getFnPlanInterest() {
        return fnPlanInterest;
    }

    public void setFnPlanInterest(Double fnPlanInterest) {
        this.fnPlanInterest = fnPlanInterest;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public String getPartnerCodeDesc() {
        return PartnerEnum.getEnumByCode(partnerCode) != null ? PartnerEnum.getEnumByCode(partnerCode).getName() : "";
    }

    public void setPartnerCodeDesc(String partnerCodeDesc) {
        this.partnerCodeDesc = partnerCodeDesc;
    }

    public String getPartnerBusinessFlag() {
        return partnerBusinessFlag;
    }

    public void setPartnerBusinessFlag(String partnerBusinessFlag) {
        this.partnerBusinessFlag = partnerBusinessFlag;
    }
}
