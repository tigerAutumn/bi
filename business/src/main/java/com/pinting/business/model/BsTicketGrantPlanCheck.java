package com.pinting.business.model;

import java.util.Date;

public class BsTicketGrantPlanCheck {
    private Integer id; // 卡券发放批次计划审核表

    private String serialNo; // 发放计划批次号

    private String serialName; // 发放计划名称

    private String ticketType; // 卡券福利类型: INTEREST_TICKET 加息券

    private String distributeType; // 发放模式：AUTO 自动 MANUAL 手动

    private Integer applicant; // 申请人

    private Date applyTime; // 申请时间

    private String checkStatus; // UNCHECKED 待审核 PASS 已通过 REFUSE 不通过

    private String grantStatus; // 发放状态: INIT 未发放 PROCESS 发放中 FINISH 发放结束 CLOSE 已停用

    private Integer checker; // 审核人

    private Date checkTime; // 审核时间

    private String note; // 备注说明

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo == null ? null : serialNo.trim();
    }

    public String getSerialName() {
        return serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName == null ? null : serialName.trim();
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType == null ? null : ticketType.trim();
    }

    public String getDistributeType() {
        return distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType == null ? null : distributeType.trim();
    }

    public Integer getApplicant() {
        return applicant;
    }

    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus == null ? null : grantStatus.trim();
    }

    public Integer getChecker() {
        return checker;
    }

    public void setChecker(Integer checker) {
        this.checker = checker;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
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