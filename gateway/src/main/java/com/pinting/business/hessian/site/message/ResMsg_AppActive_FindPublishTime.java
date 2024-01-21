package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AppActive_FindPublishTime extends ResMsg {

	private static final long serialVersionUID = -7229121133345531103L;

	private Date publishTime;

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}
}
