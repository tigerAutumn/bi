/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 未绑卡充值正式下单请求数据
 * @author HuanXiong
 * @version $Id: NoBindOrderRequest.java, v 0.1 2015-12-17 下午7:07:54 HuanXiong Exp $
 */
public class NoBindOrderRequest extends Request {

    private Integer userId;
    
    private Double amount;
    
    private Integer bankId;
    
    private String bankName;
    
    private String cardNo;
    
    private String idCard;
    
    private String mobile;
    
    private String verifyCode;
    
    private String userName;
    
    private String orderNo;
    
    private Integer terminalType;
    
    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /** 
     * @see com.pinting.open.base.request.Request#restApiUrl()
     */
    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/recharge/noBindOrder";
    }

    /** 
     * @see com.pinting.open.base.request.Request#testApiUrl()
     */
    @Override
    public String testApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/recharge/noBindOrder";
    }

}
