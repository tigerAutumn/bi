package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsWxAutoReply;

public class BsWxAutoReplyVO extends BsWxAutoReply{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2221492702921297589L;
	
	private Date startTime;
	
	private Date endTime;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

}
