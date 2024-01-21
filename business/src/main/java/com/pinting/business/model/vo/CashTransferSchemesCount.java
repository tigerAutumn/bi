package com.pinting.business.model.vo;

import java.util.List;

/**
 * Created by houjf on 2017/4/1.
 */
public class CashTransferSchemesCount {
    private Integer count;

    private List<CashTransferSchemesVO> list;

    private Double totalRepayScheduleTotalAmount;

    private Double totalPlanTotalRepaied;

    private Double totalReceivableAmount;

    public Double getTotalRepayScheduleTotalAmount() {
        return totalRepayScheduleTotalAmount;
    }

    public void setTotalRepayScheduleTotalAmount(Double totalRepayScheduleTotalAmount) {
        this.totalRepayScheduleTotalAmount = totalRepayScheduleTotalAmount;
    }

    public Double getTotalPlanTotalRepaied() {
        return totalPlanTotalRepaied;
    }

    public void setTotalPlanTotalRepaied(Double totalPlanTotalRepaied) {
        this.totalPlanTotalRepaied = totalPlanTotalRepaied;
    }

    public Double getTotalReceivableAmount() {
        return totalReceivableAmount;
    }

    public void setTotalReceivableAmount(Double totalReceivableAmount) {
        this.totalReceivableAmount = totalReceivableAmount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<CashTransferSchemesVO> getList() {
        return list;
    }

    public void setList(List<CashTransferSchemesVO> list) {
        this.list = list;
    }
}
