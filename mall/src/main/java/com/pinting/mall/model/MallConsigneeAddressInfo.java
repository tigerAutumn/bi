package com.pinting.mall.model;

import java.util.Date;

public class MallConsigneeAddressInfo {
    private Integer id; // 收货人地址信息表ID

    private Integer userId; // 用户编号ID

    private String recName; // 收货人姓名

    private String recAdress; // 收货人省市区地址

    private String recAdressDetail; // 收货人详细地址

    private String recMobile; // 收货人手机号

    private Integer isDefault; // 默认使用地址

    private Integer isDelete; // 是否废弃地址

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

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecAdress() {
        return recAdress;
    }

    public void setRecAdress(String recAdress) {
        this.recAdress = recAdress;
    }

    public String getRecAdressDetail() {
        return recAdressDetail;
    }

    public void setRecAdressDetail(String recAdressDetail) {
        this.recAdressDetail = recAdressDetail;
    }

    public String getRecMobile() {
        return recMobile;
    }

    public void setRecMobile(String recMobile) {
        this.recMobile = recMobile;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
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