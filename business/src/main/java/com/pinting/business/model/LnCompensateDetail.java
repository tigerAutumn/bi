package com.pinting.business.model;

import java.util.Date;

public class LnCompensateDetail {
    private Integer id;

    private Integer compensateId;

    private String partnerUserId;

    private String partnerLoanId;

    private String partnerRepayId;

    private Integer repaySerial;

    private String repayType;

    private Double total;

    private Double principal;

    private Double interest;

    private Double principalOverdue;

    private Double interestOverdue;

    private String status;

    private Date createTime;

    private Date updateTime;

    private String agreementGenerateStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompensateId() {
        return compensateId;
    }

    public void setCompensateId(Integer compensateId) {
        this.compensateId = compensateId;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getPartnerLoanId() {
        return partnerLoanId;
    }

    public void setPartnerLoanId(String partnerLoanId) {
        this.partnerLoanId = partnerLoanId;
    }

    public String getPartnerRepayId() {
        return partnerRepayId;
    }

    public void setPartnerRepayId(String partnerRepayId) {
        this.partnerRepayId = partnerRepayId;
    }

    public Integer getRepaySerial() {
        return repaySerial;
    }

    public void setRepaySerial(Integer repaySerial) {
        this.repaySerial = repaySerial;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getPrincipalOverdue() {
        return principalOverdue;
    }

    public void setPrincipalOverdue(Double principalOverdue) {
        this.principalOverdue = principalOverdue;
    }

    public Double getInterestOverdue() {
        return interestOverdue;
    }

    public void setInterestOverdue(Double interestOverdue) {
        this.interestOverdue = interestOverdue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getAgreementGenerateStatus() {
        return agreementGenerateStatus;
    }

    public void setAgreementGenerateStatus(String agreementGenerateStatus) {
        this.agreementGenerateStatus = agreementGenerateStatus;
    }
}