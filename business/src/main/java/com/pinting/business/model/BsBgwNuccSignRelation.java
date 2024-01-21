package com.pinting.business.model;

import java.util.Date;

public class BsBgwNuccSignRelation {

    private Integer id; // 币港湾网联清算签约关系表

    private Integer userId; // 用户编号ID

    private String userType; // 用户类型：BGW 币港湾，YUN_DAI_SELF  云贷，7_DAI_SELF  7贷

    private String merchanntNo; // 宝付商户号

    private String protocolNo; // 网联协议号

    private String cardNo; // 银行卡号

    private String cardOwner; // 开户名

    private String idCard; // 身份证号码

    private String mobile; // 银行预留手机

    private Integer status; // 1-正常，2-禁用，3-网联签约中，4.网联签约失败，5.网联解绑

    private Date bindTime; // 签约时间

    private Date unbindTime; // 解签时间

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMerchanntNo() {
        return merchanntNo;
    }

    public void setMerchanntNo(String merchanntNo) {
        this.merchanntNo = merchanntNo;
    }

    public String getProtocolNo() {
        return protocolNo;
    }

    public void setProtocolNo(String protocolNo) {
        this.protocolNo = protocolNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardOwner() {
        return cardOwner;
    }

    public void setCardOwner(String cardOwner) {
        this.cardOwner = cardOwner;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Date getUnbindTime() {
        return unbindTime;
    }

    public void setUnbindTime(Date unbindTime) {
        this.unbindTime = unbindTime;
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