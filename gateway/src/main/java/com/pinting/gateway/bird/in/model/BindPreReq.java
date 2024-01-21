package com.pinting.gateway.bird.in.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

/**
 * Created by 剑钊 on 2016/8/10.
 * 预绑卡
 */
public class BindPreReq extends BaseReqModel {

    /**
     * 预绑卡订单号
     */
    @NotEmpty(message = "绑卡编号不能为空")
    @Pattern(regexp="^[0-9a-zA-Z_]{8,64}$",message="订单号格式错误")
    private String orderNo;

    /**
     * 用户编号
     */
    @NotEmpty(message = "用户编号不能为空")
    private String userId;

    /**
     * 银行卡号
     */
    @NotEmpty(message = "卡号不能为空")
    @Pattern(regexp="^\\d{15,32}$",message="卡号格式错误")
    private String bankCard;

    /**
     * 用户姓名
     */
    @NotEmpty(message = "用户姓名不能为空")
    private String cardHolder;

    /**
     * 身份证号
     */
    @NotEmpty(message = "身份证号不能为空")
    @Pattern(regexp="^(\\d{18,18}|\\d{15,15}|\\d{17,17}x|\\d{17,17}X)$",message="身份证号格式错误")
    private String idCard;

    /**
     * 银行预留手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp="^[0-9]{11}$",message="手机号格式错误")
    private String mobile;

    /**
     * 银行编码
     */
    @NotEmpty(message = "银行编码不能为空")
    private String bankCode;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
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

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
}
