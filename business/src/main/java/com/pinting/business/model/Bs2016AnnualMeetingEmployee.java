package com.pinting.business.model;

import com.pinting.business.model.vo.PageInfoObject;

import java.util.Date;

public class Bs2016AnnualMeetingEmployee extends PageInfoObject {
    private Integer id;

    private String employeeName;

    private String companyName;

    private String isWin;

    private Integer awardId;

    private Date createTime;

    private Date updateTime;

    private Date winTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getIsWin() {
        return isWin;
    }

    public void setIsWin(String isWin) {
        this.isWin = isWin == null ? null : isWin.trim();
    }

    public Integer getAwardId() {
        return awardId;
    }

    public void setAwardId(Integer awardId) {
        this.awardId = awardId;
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

    public Date getWinTime() {
        return winTime;
    }

    public void setWinTime(Date winTime) {
        this.winTime = winTime;
    }
}