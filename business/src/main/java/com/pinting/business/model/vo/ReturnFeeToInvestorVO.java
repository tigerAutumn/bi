package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by zhangbao on 2017/11/15.
 */
public class ReturnFeeToInvestorVO {

    private Integer rowno; //查询结果序号

    private String orderNo; //单据编号

    private String userName; //投资人

    private String userId; //投资人用户编号

    private String mobile; //手机号

    private String partnerCode;//资产方标识

    private Double returnFeeAmount; //返还手续费金额

    private Date time; //返还日期

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getReturnFeeAmount() {
        return returnFeeAmount;
    }

    public void setReturnFeeAmount(Double returnFeeAmount) {
        this.returnFeeAmount = returnFeeAmount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
