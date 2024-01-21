package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;


public class Bs19payBank extends PageInfoObject{
    private Integer id;

    private Integer bankId;

    private String channel;

    private Integer channelPriority;

    private Integer isMain;

    private String pay19BankCode;

    private Integer payType;

    private Double oneTop;

    private Double dayTop;

    private Double monthTop;

    private Date forbiddenStart;

    private Date forbiddenEnd;

    private Integer isAvailable;

    private String notice;

    private String dailyNotice;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getChannelPriority() {
        return channelPriority;
    }

    public void setChannelPriority(Integer channelPriority) {
        this.channelPriority = channelPriority;
    }

    public Integer getIsMain() {
        return isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

    public String getPay19BankCode() {
        return pay19BankCode;
    }

    public void setPay19BankCode(String pay19BankCode) {
        this.pay19BankCode = pay19BankCode;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Double getOneTop() {
        return oneTop;
    }

    public void setOneTop(Double oneTop) {
        this.oneTop = oneTop;
    }

    public Double getDayTop() {
        return dayTop;
    }

    public void setDayTop(Double dayTop) {
        this.dayTop = dayTop;
    }

    public Double getMonthTop() {
        return monthTop;
    }

    public void setMonthTop(Double monthTop) {
        this.monthTop = monthTop;
    }

    public Date getForbiddenStart() {
        return forbiddenStart;
    }

    public void setForbiddenStart(Date forbiddenStart) {
        this.forbiddenStart = forbiddenStart;
    }

    public Date getForbiddenEnd() {
        return forbiddenEnd;
    }

    public void setForbiddenEnd(Date forbiddenEnd) {
        this.forbiddenEnd = forbiddenEnd;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDailyNotice() {
        return dailyNotice;
    }

    public void setDailyNotice(String dailyNotice) {
        this.dailyNotice = dailyNotice;
    }
}