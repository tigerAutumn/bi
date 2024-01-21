package com.pinting.business.model;

import java.util.Date;

public class BsInterestTicketInfo {
    private Integer id; // 加息券信息表

    private String serialNo; // 批次号

    private Integer userId; // 用户编号ID

    private String status; // 状态：INIT 未使用 BUYING 购买使用中 USED 已使用

    private Double ticketApr; // 基于本金的加息率(%)

    private Integer authAccountId; // 理财人投资产品站岗户ID

    private String orderNo; // 购买订单号

    private Double interestAmount; // 购买成功后收益金额

    private Date useTime; // 加息券使用时间

    private Date useTimeStart; // 使用有效期开始时间

    private Date useTimeEnd; // 使用有效期结束时间

    private String msgStatus; // 消息发送状态： NOT 未发送 FINISHED 已发送

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Double getTicketApr() {
        return ticketApr;
    }

    public void setTicketApr(Double ticketApr) {
        this.ticketApr = ticketApr;
    }

    public Integer getAuthAccountId() {
        return authAccountId;
    }

    public void setAuthAccountId(Integer authAccountId) {
        this.authAccountId = authAccountId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public Double getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(Double interestAmount) {
        this.interestAmount = interestAmount;
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
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

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus == null ? null : msgStatus.trim();
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