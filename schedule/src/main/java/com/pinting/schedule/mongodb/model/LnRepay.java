package com.pinting.schedule.mongodb.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document( collection = "LnRepay")
public class LnRepay {
	@Field("_id")
	private Object Id;
	
	@Field("id")
	private Integer id;

    @Field("ln_user_id")
    private Integer lnUserId;

    @Field("loan_id")
    private Integer loanId;

    @Field("repay_plan_id")
    private Integer repayPlanId;

    @Field("partner_order_no")
    private String partnerOrderNo;

    @Field("bgw_order_no")
    private String bgwOrderNo;

    @Field("bgw_bind_id")
    private String bgwBindId;

    @Field("pay_order_no")
    private String payOrderNo;

    @Field("done_time")
    private Date doneTime;

    @Field("done_total")
    private Double doneTotal;

    private String status;

    @Field("inform_status")
    private String informStatus;
    
    @Field("repay_type")
	private String repayType;

    @Field("create_time")
    private Date createTime;

    @Field("update_time")
    private Date updateTime;

    @Field("apply_time")
    private Date applyTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getRepayPlanId() {
        return repayPlanId;
    }

    public void setRepayPlanId(Integer repayPlanId) {
        this.repayPlanId = repayPlanId;
    }

    public String getPartnerOrderNo() {
        return partnerOrderNo;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo;
    }

    public String getBgwOrderNo() {
        return bgwOrderNo;
    }

    public void setBgwOrderNo(String bgwOrderNo) {
        this.bgwOrderNo = bgwOrderNo;
    }

    public String getBgwBindId() {
        return bgwBindId;
    }

    public void setBgwBindId(String bgwBindId) {
        this.bgwBindId = bgwBindId;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public Double getDoneTotal() {
        return doneTotal;
    }

    public void setDoneTotal(Double doneTotal) {
        this.doneTotal = doneTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(String informStatus) {
        this.informStatus = informStatus;
    }
    

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
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

    public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	@Override
    public String toString() {
        return "LnRepay{" +
                "id=" + id +
                ", lnUserId=" + lnUserId +
                ", loanId=" + loanId +
                ", repayPlanId=" + repayPlanId +
                ", partnerOrderNo='" + partnerOrderNo + '\'' +
                ", bgwOrderNo='" + bgwOrderNo + '\'' +
                ", bgwBindId='" + bgwBindId + '\'' +
                ", payOrderNo='" + payOrderNo + '\'' +
                ", doneTime=" + doneTime +
                ", doneTotal=" + doneTotal +
                ", status='" + status + '\'' +
                ", informStatus='" + informStatus + '\'' +
                ", repayType='" + repayType + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", applyTime=" + applyTime +
                '}';
    }
}