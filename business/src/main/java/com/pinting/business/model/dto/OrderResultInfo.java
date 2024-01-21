package com.pinting.business.model.dto;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/11.
 */
public class OrderResultInfo extends ResMsg {
    private boolean isSuccess;//状态
    private String orderNo;//我方订单号
    private Double amount;//交易金额
    private String payOrderNo;//支付方订单号
    private String returnCode;//返回码
    private String returnMsg;//返回信息
    private Date finishTime;//订单完成时间

    private String hfUserId;// 恒丰客户号
    private String payPath;// 支付通道
    
    public String getHfUserId() {
        return hfUserId;
    }

    public void setHfUserId(String hfUserId) {
        this.hfUserId = hfUserId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
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

	public String getPayPath() {
		return payPath;
	}

	public void setPayPath(String payPath) {
		this.payPath = payPath;
	}
    
}
