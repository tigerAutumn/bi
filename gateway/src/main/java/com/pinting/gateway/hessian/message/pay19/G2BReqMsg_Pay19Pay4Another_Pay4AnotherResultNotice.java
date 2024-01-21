package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Pay19Pay4Another_Pay4AnotherResultNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3140208299306163220L;
	private String            mxOrderId;
    private String            sysOrderId;
    private String            orderStatus;
    private Date              finishTime;
    private Double            amount;
    private String            retCode;
    private String            errorMsg;
    private String            hmac;
    private String            extend1;
    private String            extend2;
    private String            extend3;
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
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public String getExtend1() {
		return extend1;
	}
	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	public String getExtend2() {
		return extend2;
	}
	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	public String getExtend3() {
		return extend3;
	}
	public void setExtend3(String extend3) {
		this.extend3 = extend3;
	}
    
}
