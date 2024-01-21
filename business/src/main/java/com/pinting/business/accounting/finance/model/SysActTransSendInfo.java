package com.pinting.business.accounting.finance.model;

import java.util.HashMap;

/**
 * Created by babyshark on 2016/9/6.
 */
public class SysActTransSendInfo {

    /**
     * 商户订单号
     * 商户唯一流水号
     */
    private String transNo;

    /**
     * 转账金额
     * 单位：元
     */
    private Double transMoney;

    /**
     * 收款人账户名称
     */
    private String toAccName;

    /**
     * 收款人注册帐号
     */
    private String toAccNo;

    /**
     * 收款方会员号
     */
    private String toMemberId;

    /**
     * 摘要
     */
    private String transSummary;
    
    /**
     * 合作资产方编号
     */
    private String propertySymbol;
    /**
     * 扩展字段
     */
    HashMap<String, Object> extendMap;

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public Double getTransMoney() {
        return transMoney;
    }

    public void setTransMoney(Double transMoney) {
        this.transMoney = transMoney;
    }

    public String getToAccName() {
        return toAccName;
    }

    public void setToAccName(String toAccName) {
        this.toAccName = toAccName;
    }

    public String getToAccNo() {
        return toAccNo;
    }

    public void setToAccNo(String toAccNo) {
        this.toAccNo = toAccNo;
    }

    public String getToMemberId() {
        return toMemberId;
    }

    public void setToMemberId(String toMemberId) {
        this.toMemberId = toMemberId;
    }

    public String getTransSummary() {
        return transSummary;
    }

    public void setTransSummary(String transSummary) {
        this.transSummary = transSummary;
    }

    public String getPropertySymbol() {
        return propertySymbol;
    }

    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
    }

    public HashMap<String, Object> getExtendMap() {
        return extendMap;
    }

    public void setExtendMap(HashMap<String, Object> extendMap) {
        this.extendMap = extendMap;
    }
}
