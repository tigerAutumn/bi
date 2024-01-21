package com.pinting.business.model;

import java.util.Date;

public class BsFileSealRelation {
    private Integer id;

    private Integer sealFileId;

    private String contentType;

    private String keywords;

    private Integer userSealId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSealFileId() {
        return sealFileId;
    }

    public void setSealFileId(Integer sealFileId) {
        this.sealFileId = sealFileId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getUserSealId() {
        return userSealId;
    }

    public void setUserSealId(Integer userSealId) {
        this.userSealId = userSealId;
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