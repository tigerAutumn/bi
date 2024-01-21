package com.pinting.business.model;

import java.util.Date;

public class BsInterestTicketGrantAttribute {
    private Integer id; // 加息券发放批次属性表

    private String serialNo; // 发放计划批次号

    private String ticketName; // 加息券名称

    private Double ticketApr; // 加息幅度(基于本金的加息率%)

    private Integer grantTotal; // 发放加息券总数

    private Integer grantNum; // 已发放加息券数量

    private String validTermType; // 加息券发放有效期类型:FIXED 固定时间段有效 AFTER_RECEIVE 发放后有效天数

    private Date useTimeStart; // FIXED 固定时间段：使用有效期开始

    private Date useTimeEnd; // FIXED 固定时间段：使用有效期结束

    private Integer availableDays; // AFTER_RECEIVE 发放后有效天数：使用

    private String notifyChannel; // WECHAT 微信 SMS 短信 APP app通知 可以多个，以逗号隔开

    private Double investLimit; // 达到投资金额加息券才能使用(单笔投资满多少元)

    private String productLimit; // BIGANGWAN_SERIAL（港湾系列） YONGJIN_SERIAL（涌金系列） KUAHONG_SERIAL（跨虹系列） BAOXIN_SERIAL（保信系列） 多个产品限制用逗号隔开

    private String termLimit; // 30,90,180,365（天） 多个产品期限限制用逗号隔开

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

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName == null ? null : ticketName.trim();
    }

    public Double getTicketApr() {
        return ticketApr;
    }

    public void setTicketApr(Double ticketApr) {
        this.ticketApr = ticketApr;
    }

    public Integer getGrantTotal() {
        return grantTotal;
    }

    public void setGrantTotal(Integer grantTotal) {
        this.grantTotal = grantTotal;
    }

    public Integer getGrantNum() {
        return grantNum;
    }

    public void setGrantNum(Integer grantNum) {
        this.grantNum = grantNum;
    }

    public String getValidTermType() {
        return validTermType;
    }

    public void setValidTermType(String validTermType) {
        this.validTermType = validTermType == null ? null : validTermType.trim();
    }

    public Date getUseTimeStart() {
        return useTimeStart;
    }

    public void setUseTimeStart(Date useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public Date getUseTimeEnd() {
        return useTimeEnd;
    }

    public void setUseTimeEnd(Date useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public Integer getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Integer availableDays) {
        this.availableDays = availableDays;
    }

    public String getNotifyChannel() {
        return notifyChannel;
    }

    public void setNotifyChannel(String notifyChannel) {
        this.notifyChannel = notifyChannel == null ? null : notifyChannel.trim();
    }

    public Double getInvestLimit() {
        return investLimit;
    }

    public void setInvestLimit(Double investLimit) {
        this.investLimit = investLimit;
    }

    public String getProductLimit() {
        return productLimit;
    }

    public void setProductLimit(String productLimit) {
        this.productLimit = productLimit == null ? null : productLimit.trim();
    }

    public String getTermLimit() {
        return termLimit;
    }

    public void setTermLimit(String termLimit) {
        this.termLimit = termLimit == null ? null : termLimit.trim();
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