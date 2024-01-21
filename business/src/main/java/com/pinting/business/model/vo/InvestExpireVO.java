package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by zhangbao on 2017/11/17.
 */
public class InvestExpireVO {

    private Integer rowno; //查询结果序号

    private String orderNo; //单据编号

    private String userName; //投资人

    private String customerCode; //投资客户代码

    private Double corpusAndInterest; //到期本金及利息

    private Date time; //兑付日期

    private String productType; //理财账户类型

    public Integer getRowno() {
        return rowno;
    }

    public void setRowno(Integer rowno) {
        this.rowno = rowno;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Double getCorpusAndInterest() {
        return corpusAndInterest;
    }

    public void setCorpusAndInterest(Double corpusAndInterest) {
        this.corpusAndInterest = corpusAndInterest;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
