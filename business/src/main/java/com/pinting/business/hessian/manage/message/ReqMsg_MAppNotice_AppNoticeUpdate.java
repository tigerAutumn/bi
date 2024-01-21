package com.pinting.business.hessian.manage.message;

import com.pinting.business.model.BsAppMessage;
import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAppNotice_AppNoticeUpdate extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3944473771131305243L;
	
	private BsAppMessage appMessage;

	public BsAppMessage getAppMessage() {
		return appMessage;
	}

	public void setAppMessage(BsAppMessage appMessage) {
		this.appMessage = appMessage;
	}
}
