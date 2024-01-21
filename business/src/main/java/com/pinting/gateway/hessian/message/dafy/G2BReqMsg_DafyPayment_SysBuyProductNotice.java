package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @Project: gateway
 * @Title: G2BReqMsg_DafyPayment_SysBuyProductNotice.java
 * @author dingpf
 * @date 2015-11-21 下午12:37:22
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class G2BReqMsg_DafyPayment_SysBuyProductNotice extends ReqMsg{
	public static final int BUY_RESULT_SUCCESS = 1;//交易成功
	public static final int BUY_RESULT_FAIL = 2;//交易失败
	/**
	 * 
	 */
	private static final long serialVersionUID = -8816529091495485035L;
	private String payPlatform;
	private Date finishTime;
	private String productOrderNo;
	private String productCode;
	private double productAmount;
	private int result;
	private String errorMsg;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	public String getPayPlatform() {
		return payPlatform;
	}
	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}
	public String getProductOrderNo() {
		return productOrderNo;
	}
	public void setProductOrderNo(String productOrderNo) {
		this.productOrderNo = productOrderNo;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public double getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(double productAmount) {
		this.productAmount = productAmount;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "G2BReqMsg_DafyPayment_SysBuyProductNotice [payPlatform="
				+ payPlatform + ", finishTime=" + finishTime
				+ ", productOrderNo=" + productOrderNo + ", productCode="
				+ productCode + ", productAmount=" + productAmount
				+ ", result=" + result + "]";
	}

}
