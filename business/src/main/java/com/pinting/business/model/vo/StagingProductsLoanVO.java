package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 管理台 分期产品出借查询数据
 * Created by shh on 2016/11/7 19:39.
 */
public class StagingProductsLoanVO extends PageInfoObject {

    /** 订单号 */
    private String orderNo;
    private Integer userId;
    /** 期数 */
    private Integer period;
    /** 出借金额 */
    private Double totalAmount;
    /** 服务费 */
    private Double serviceAmount;
    /** 成交日期 */
    private Date loanTime;
    /** 渠道 */
    private String agentName;

    private Date loanTimeStart;
    private Date loanTimeEnd;

    /**
     * 借款关系id
     */
    private Integer loanRelationId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getServiceAmount() {
        return serviceAmount;
    }

    public void setServiceAmount(Double serviceAmount) {
        this.serviceAmount = serviceAmount;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Date getLoanTimeStart() {
        return loanTimeStart;
    }

    public void setLoanTimeStart(Date loanTimeStart) {
        this.loanTimeStart = loanTimeStart;
    }

    public Date getLoanTimeEnd() {
        return loanTimeEnd;
    }

    public void setLoanTimeEnd(Date loanTimeEnd) {
        this.loanTimeEnd = loanTimeEnd;
    }

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }
}
