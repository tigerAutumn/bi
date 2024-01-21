package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 
 * @author HuanXiong
 * @version 2016-6-1 下午4:29:16
 */
public class UserDrawBonusStatisticsVO {
    
    private Integer rowNo; //查询结果序号
    
    private String orderNo; // 订单号
    
    private String customerCode;    // 客户代码
    
    private String customerName;    // 客户名称
    
    private Double amount;  // 金额
    
    private Date updateTime;

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
}
