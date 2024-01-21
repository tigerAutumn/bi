package com.pinting.business.model.dto;

import java.util.Date;

/**
 * Created by 剑钊
 *
 * @2016/9/18 16:29.
 */
public class TransPartnerRevenueInfo {

    private String status;//状态 0为支付中 1为成功 -1为失败
    private String orderNo;//我方订单号
    private Integer recordId;//营收转账记录id
    private Double amount;//交易金额
    private String returnCode;//返回码
    private String returnMsg;//返回信息
    private Date finishTime;//订单完成时间

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
}
