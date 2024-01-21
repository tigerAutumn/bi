package com.pinting.business.accounting.loan.model;

import com.pinting.core.hessian.msg.ReqMsg;

public class DepFixedRepayPayResultInfo extends ReqMsg{

	private static final long serialVersionUID = -794178619783221896L;
	
	private boolean isSuccess;//状态
    private String orderNo;//我方订单号
    private Double amount;//交易金额
    private String returnCode;//返回码
    private String returnMsg;//返回信息
    
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
