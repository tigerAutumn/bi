package com.pinting.gateway.dafy.out.model;

import java.util.Date;
/**
 * 自主放款-待补账通知
 * @author Dragon & cat
 * @date 2016-11-24
 */
public class WaitFillReqModel extends BaseReqModel {
	
	/*补账单号*/
	private		String		orderNo;
	/*待补账日期*/
	private		Date		fillDate;
	/*补账类型*/
	private		String		fillType;
	/*补账金额*/
	private		Long		amount;
	/*申请流水号*/
	private	String	requestSeq;
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

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public String getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
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

	@Override
	public String toString() {
		return "WaitFillReqModel [orderNo=" + orderNo + ", fillDate="
				+ fillDate + ", fillType=" + fillType + ", amount=" + amount
				+ ", requestSeq=" + requestSeq + ", fileUrl=" + fileUrl + "]";
	}

	
}
