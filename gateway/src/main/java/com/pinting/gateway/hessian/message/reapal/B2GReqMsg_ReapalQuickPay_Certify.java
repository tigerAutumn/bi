package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_ReapalQuickPay_Certify extends ReqMsg {

	private static final long serialVersionUID = 8587293559881717544L;

	private String orderNo;
	
	private String bindId;
	
	private Integer userId;
	
	private Integer source;
	
	private Integer pid; //产品ID

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBindId() {
		return bindId;
	}

	public void setBindId(String bindId) {
		this.bindId = bindId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
}
