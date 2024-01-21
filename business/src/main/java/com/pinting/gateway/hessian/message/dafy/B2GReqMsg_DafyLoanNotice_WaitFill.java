package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_DafyLoanNotice_WaitFill extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2785109270744815474L;
	/*补账单号*/
	private		String		orderNo;
	/*待补账日期*/
	private		Date		fillDate;
	/*补账类型*/
	private		String		fillType;
	/*补账金额*/
	private		Double		amount;
	/*补账明细文件下载地址*/
	private		String		fileUrl;
	
	public Date getFillDate() {
		return fillDate;
	}
	public void setFillDate(Date fillDate) {
		this.fillDate = fillDate;
	}
	public String getFillType() {
		return fillType;
	}
	public void setFillType(String fillType) {
		this.fillType = fillType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
