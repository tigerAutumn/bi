package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Customer_GetLoanRelationNew extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5162629449133367545L;

	private String orderNo; //理财购买单号
	private String pageIndex; //页码 1：第一页2：第二页
	private String pageNum; //每页条数
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
}
