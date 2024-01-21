package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 用户地址管理VO
 *
 * @author shh
 * @date 2018/5/28 14:13
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class UserAddressInfoVO {

    /* 姓名 */
    private String userName;

    /* 注册手机号 */
    private String mobile;

    /* 收货地址 */
    private String consigneeAddress;

    /* 地址更新时间 */
    private Date updateTime;

    /* 用户编号 */
    private Integer userId;

    /* 收件人 */
    private String consignee;

    /* 收件人手机号码 */
    private String consigneeMobile;

    /* 地址是否默认值 */
    private String isDefault;

    /* 地址信息表id */
    private Integer addressInfoId;

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

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeMobile() {
        return consigneeMobile;
    }

    public void setConsigneeMobile(String consigneeMobile) {
        this.consigneeMobile = consigneeMobile;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getAddressInfoId() {
        return addressInfoId;
    }

    public void setAddressInfoId(Integer addressInfoId) {
        this.addressInfoId = addressInfoId;
    }
}
