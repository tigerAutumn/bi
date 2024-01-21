package com.pinting.business.accounting.finance.model;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/11.
 */
public class EBankResultInfo extends ReqMsg {
    private String mxOrderId;//订单号
    private Double amount;//交易金额
    private String mpOrderId;//支付订单号
    private Date payDate;//支付完成时间
    private String result;//交易状态
    private String retCode;//返回码
    private String errorMsg;//返回信息

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMpOrderId() {
        return mpOrderId;
    }

    public void setMpOrderId(String mpOrderId) {
        this.mpOrderId = mpOrderId;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
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
