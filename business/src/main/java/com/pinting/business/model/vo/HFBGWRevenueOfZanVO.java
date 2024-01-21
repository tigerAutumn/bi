package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 财务统计：恒丰币港湾营收（赞分期）
 * Created by cyb on 2018/4/20.
 */
public class HFBGWRevenueOfZanVO {

    // 投资人姓名
    private String userName;

    // 投资人手机号
    private String mobile;

    // 资产方
    private String partnerCode;

    // 出借金额
    private Double totalAmount;

    // 币港湾营收（恒丰）
    private Double transAmount;

    // 结算日期
    private Date transTime;

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

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }
}
