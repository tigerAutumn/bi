package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/6/16
 * Description: 云贷砍头息代收代付VO
 */
public class RevenueTransDetailVO {

    /* 融资客户名称 */
    private String userName;

    /* 手机号 */
    private String mobile;

    /* 类型 代收/代付 */
    private String transType;

    /* 借款金额 */
    private Double applyAmount;

    /* 代收金额 */
    private Double revenueAmount;

    /* 代付金额 */
    private Double transAmount;

    /* 产生日期 */
    private Date  createTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Double getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(Double applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Double getRevenueAmount() {
        return revenueAmount;
    }

    public void setRevenueAmount(Double revenueAmount) {
        this.revenueAmount = revenueAmount;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}