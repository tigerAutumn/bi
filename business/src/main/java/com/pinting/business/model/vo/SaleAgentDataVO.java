/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.model.vo;

/**
 * 
 * @author HuanXiong
 * @version $Id: SaleAgentDataVO.java, v 0.1 2016-2-19 上午9:48:36 HuanXiong Exp $
 */
public class SaleAgentDataVO {
    private Integer agentId;
    private String agentName;
    private Integer term;
    private Double balance;
    private Double baseRate;
    private Double cp;
    private String productName;
    public Integer getAgentId() {
        return agentId;
    }
    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
    public String getAgentName() {
        return agentName;
    }
    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }
    public Integer getTerm() {
        return term;
    }
    public void setTerm(Integer term) {
        this.term = term;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public Double getBaseRate() {
        return baseRate;
    }
    public void setBaseRate(Double baseRate) {
        this.baseRate = baseRate;
    }
    public Double getCp() {
        return cp;
    }
    public void setCp(Double cp) {
        this.cp = cp;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
