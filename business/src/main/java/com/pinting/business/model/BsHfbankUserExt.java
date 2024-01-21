package com.pinting.business.model;

import java.util.Date;

public class BsHfbankUserExt {
    private Integer id;

    private Integer userId;

    private String hfUserId;

    private Date hfRegistTime;

    private Date hfBindCardTime;

    private String status;

    private String note;

    private Date createTime;

    private Date updateTime;

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

    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

    public Date getHfRegistTime() {
        return hfRegistTime;
    }

    public void setHfRegistTime(Date hfRegistTime) {
        this.hfRegistTime = hfRegistTime;
    }

    public Date getHfBindCardTime() {
        return hfBindCardTime;
    }

    public void setHfBindCardTime(Date hfBindCardTime) {
        this.hfBindCardTime = hfBindCardTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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