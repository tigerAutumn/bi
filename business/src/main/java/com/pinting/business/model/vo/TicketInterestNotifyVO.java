package com.pinting.business.model.vo;

public class TicketInterestNotifyVO {

    private Integer id; // 加息券信息表ID

    private String serialNo; // 批次号

    private Integer userId; // 用户编号ID

    private String mobile; // 用户手机号

    private Double ticketApr; // 基于本金的加息率(%)

    private String ticketName; // 加息券名称

    private String notifyChannel; // 通知渠道 WECHAT 微信 SMS 短信 APP app通知 可以多个，以逗号隔开

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
        this.serialNo = serialNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Double getTicketApr() {
        return ticketApr;
    }

    public void setTicketApr(Double ticketApr) {
        this.ticketApr = ticketApr;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getNotifyChannel() {
        return notifyChannel;
    }

    public void setNotifyChannel(String notifyChannel) {
        this.notifyChannel = notifyChannel;
    }
}