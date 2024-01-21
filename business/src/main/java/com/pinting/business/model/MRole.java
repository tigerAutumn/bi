package com.pinting.business.model;

import java.util.Date;

public class MRole {
    private Integer id;

    private String name;

    private Date createTime;

    private String note;

    private Integer dafyRoleId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getDafyRoleId() {
        return dafyRoleId;
    }

    public void setDafyRoleId(Integer dafyRoleId) {
        this.dafyRoleId = dafyRoleId;
    }
}