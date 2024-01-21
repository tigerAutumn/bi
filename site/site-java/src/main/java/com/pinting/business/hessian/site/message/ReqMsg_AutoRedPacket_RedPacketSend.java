/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 自动发放红包准则解析，发放红包入参
 * @author yanwl
 * @version $Id: ReqMsg_AutoRedPacket_RedPacketSend.java, v 0.1 2016-3-4 上午10:50:42 yanwl Exp $
 */
public class ReqMsg_AutoRedPacket_RedPacketSend extends ReqMsg {

    /**  
     * 序列化编号
     */
    private static final long serialVersionUID = 5251287360520113969L;

    /**
	 * 用户Id
	 */
	private Integer userId;
	
	/**
	 * 触发条件类型
	 */
	private String triggerType;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}
}
