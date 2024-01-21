package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAppPushMessage_AppPushMessageAdd extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 5624180080814476620L;
	
	private Integer userId;
	
	private Integer messageId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
}
