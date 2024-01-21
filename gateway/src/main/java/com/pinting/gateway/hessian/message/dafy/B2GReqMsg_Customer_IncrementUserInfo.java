package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Customer_IncrementUserInfo extends ReqMsg {

	private static final long serialVersionUID = 3629572047903978145L;
	
	private String dtUpdateTime;
	
	private Integer pageIndex;

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getDtUpdateTime() {
		return dtUpdateTime;
	}

	public void setDtUpdateTime(String dtUpdateTime) {
		this.dtUpdateTime = dtUpdateTime;
	}
}
