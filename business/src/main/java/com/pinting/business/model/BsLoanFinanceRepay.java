package com.pinting.business.model;

import java.util.Date;

public class BsLoanFinanceRepay {
    private Integer id;

    private Integer fnUserId;

    private Integer financeRepayScheduleId;

    private String orderNo;

    private Double total;

    private Double principal;

    private Double interest;

    private String status;

    private Date createTime;

    private Date updateTime;

    private Date planDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFnUserId() {
        return fnUserId;
    }

    public void setFnUserId(Integer fnUserId) {
        this.fnUserId = fnUserId;
    }

    public Integer getFinanceRepayScheduleId() {
        return financeRepayScheduleId;
    }

    public void setFinanceRepayScheduleId(Integer financeRepayScheduleId) {
        this.financeRepayScheduleId = financeRepayScheduleId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
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

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }
}