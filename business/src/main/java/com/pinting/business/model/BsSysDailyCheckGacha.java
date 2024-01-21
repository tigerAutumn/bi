package com.pinting.business.model;

import java.util.Date;

public class BsSysDailyCheckGacha {
    private Integer id;

    private Date checkDate;

    private String channel;

    private String partnerCode;

    private String merchantNo;

    private String financialFlag;

    private String businessType;

    private Double transferSuccAmount;

    private Integer transferSuccCount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode == null ? null : partnerCode.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getFinancialFlag() {
        return financialFlag;
    }

    public void setFinancialFlag(String financialFlag) {
        this.financialFlag = financialFlag == null ? null : financialFlag.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public Double getTransferSuccAmount() {
        return transferSuccAmount;
    }

    public void setTransferSuccAmount(Double transferSuccAmount) {
        this.transferSuccAmount = transferSuccAmount;
    }

    public Integer getTransferSuccCount() {
        return transferSuccCount;
    }

    public void setTransferSuccCount(Integer transferSuccCount) {
        this.transferSuccCount = transferSuccCount;
    }
}