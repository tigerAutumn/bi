package com.pinting.schedule.mongodb.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document ( collection = "LnCompensateDetail")
public class LnCompensateDetail {
	@Field("id")
    private Integer id;

    @Field("compensate_id")
    private Integer compensateId;

    @Field("partner_user_id")
    private String partnerUserId;

    @Field("partner_loan_id")
    private String partnerLoanId;

    @Field("partner_repay_id")
    private String partnerRepayId;

    @Field("repay_serial")
    private Integer repaySerial;

    @Field("repay_type")
    private String repayType;

    private Double total;

    private Double principal;

    private Double interest;

    @Field("principal_overdue")
    private Double principalOverdue;

    @Field("interest_overdue")
    private Double interestOverdue;

    private String status;
    
    @Field("create_time")
    private Date createTime;

    @Field("update_time")
    private Date updateTime;

    @Field("agreement_generate_status")
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