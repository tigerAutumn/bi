package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.pay19.enums.TradeResult;


public class B2GResMsg_QuickPay_ConfirmOrder extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 390881519485189367L;
	private String            errorCode;
    private TradeResult       tradeResult;
    private String            userId;
    private String            orderId;
    private Date              orderDate;
    private String            mpOrderId;
    private Date              finTime;
    private String            errorMsg;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public TradeResult getTradeResult() {
        return tradeResult;
    }

    public void setTradeResult(TradeResult tradeResult) {
        this.tradeResult = tradeResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getMpOrderId() {
        return mpOrderId;
    }

    public void setMpOrderId(String mpOrderId) {
        this.mpOrderId = mpOrderId;
    }

    public Date getFinTime() {
        return finTime;
    }

    public void setFinTime(Date finTime) {
        this.finTime = finTime;
    }
}
