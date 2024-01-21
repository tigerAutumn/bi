package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 
 * @author HuanXiong
 * @version 2016-6-1 下午2:31:14
 */
public class UserRedPublishStatisticsVO {
    
    private Integer rowNo; //查询结果序号
    
    private String orderNo; // 订单号
    
    private String dept;    // 部门
    
    private String customerCode;    // 客户代码
    
    private String customerName;    // 客户名称
    
    private Double amount;  // 红包金额
    
    private Integer id; // 红包ID
    
    private Date createTime;
    
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

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    
}
