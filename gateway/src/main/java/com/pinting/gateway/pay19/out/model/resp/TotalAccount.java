/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: TotalAccount.java, v 0.1 2015-8-31 下午8:13:56 BabyShark Exp $
 */
public class TotalAccount implements Serializable {

    /**  */
    private static final long serialVersionUID = -2350555720042228399L;

    private Date              accountDate;
    private Integer           orderCount;
    private Double            totalIncome;
    private Double            totalDebit;
    private Double            totalRefund;
    private Double            totalFee;
    private Double            totalClearing;
    private Double            totalSettle;

    public Date getAccountDate() {
        return accountDate;
    }

    public void setAccountDate(Date accountDate) {
        this.accountDate = accountDate;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalDebit() {
        return totalDebit;
    }

    public void setTotalDebit(Double totalDebit) {
        this.totalDebit = totalDebit;
    }

    public Double getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Double totalRefund) {
        this.totalRefund = totalRefund;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getTotalClearing() {
        return totalClearing;
    }

    public void setTotalClearing(Double totalClearing) {
        this.totalClearing = totalClearing;
    }

    public Double getTotalSettle() {
        return totalSettle;
    }

    public void setTotalSettle(Double totalSettle) {
        this.totalSettle = totalSettle;
    }

}
