package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_WeekHayActivity_CountUserRemind extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5165263743140929143L;
	
	/** 已设置提醒的条数*/
	private Integer remindCount;

	public Integer getRemindCount() {
		return remindCount;
	}

	public void setRemindCount(Integer remindCount) {
		this.remindCount = remindCount;
	}
	
	

}
