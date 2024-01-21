/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 根据银行卡ID查询银行卡出参
 * @author HuanXiong
 * @version $Id: ResMsg_Bank_QueryCardByUserId.java, v 0.1 2015-11-21 下午3:06:10 HuanXiong Exp $
 */
public class ResMsg_Bank_QueryCardById extends ResMsg {
    
    /**  */
    private static final long serialVersionUID = -4705250356571171602L;
    /*银行卡信息表主键id*/
    private Integer id;
    /*用户id*/
    private Integer userId;
    /*银行卡号*/
    private String cardNo;
    /*开户名*/
    private String cardOwner;
    /*用户身份证号*/
    private String idCard;
    /*银行预留手机*/
    private String mobile;
    /*银行id*/
    private Integer bankId;
    /*状态*/
    private Integer status;
    /*是否常用卡*/
    private Integer isFirst;
    /*绑定时间*/
    private Date bindTime;
    /*解绑时间*/
    private Date unbindTime;
    /*开户行*/
    private String bankName;
    /*支行名称*/
    private String subBranchName;
    /*开户省份id*/
    private Integer openProvinceId;
    /*开户城市id*/
    private Integer openCityId;

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

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getSubBranchName() {
        return subBranchName;
    }

    public void setSubBranchName(String subBranchName) {
        this.subBranchName = subBranchName;
    }

    public Integer getOpenProvinceId() {
        return openProvinceId;
    }

    public void setOpenProvinceId(Integer openProvinceId) {
        this.openProvinceId = openProvinceId;
    }

    public Integer getOpenCityId() {
        return openCityId;
    }

    public void setOpenCityId(Integer openCityId) {
        this.openCityId = openCityId;
    }
}
