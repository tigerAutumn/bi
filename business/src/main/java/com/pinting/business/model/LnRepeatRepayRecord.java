package com.pinting.business.model;

import java.util.Date;

public class LnRepeatRepayRecord {
    private Integer id;

    private Integer repayPlanId;

    private String partnerCode;

    private String repayOrderNo;

    private String repayType;

    private Double repayAmount;

    private Double returnAmount;

    private Date settleDate;

    private Date finishTime;

    private String status;

    private Date createTime;

    private Date updateTime;

    private Double marginAmount;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepayPlanId() {
        return repayPlanId;
    }

    public void setRepayPlanId(Integer repayPlanId) {
        this.repayPlanId = repayPlanId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getRepayOrderNo() {
        return repayOrderNo;
    }

    public void setRepayOrderNo(String repayOrderNo) {
        this.repayOrderNo = repayOrderNo;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public Double getReturnAmount() {
        return returnAmount;
    }

    public void setReturnAmount(Double returnAmount) {
        this.returnAmount = returnAmount;
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

	public Double getMarginAmount() {
		return marginAmount;
	}

	public void setMarginAmount(Double marginAmount) {
		this.marginAmount = marginAmount;
	}
}