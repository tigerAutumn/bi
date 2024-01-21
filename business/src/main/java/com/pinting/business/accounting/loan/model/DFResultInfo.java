package com.pinting.business.accounting.loan.model;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/11.
 */
public class DFResultInfo extends ReqMsg {
    private String            mxOrderId;//订单号
    private String            sysOrderId;//支付订单号
    private String            orderStatus;//交易状态
    private Date              finishTime;//完成时间
    private Double            amount;//交易金额
    private String            retCode;//返回码
    private String            errorMsg;//返回信息

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public String getSysOrderId() {
        return sysOrderId;
    }

    public void setSysOrderId(String sysOrderId) {
        this.sysOrderId = sysOrderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
