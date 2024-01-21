/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 自动发放红包准则解析，发放红包出参
 * @author yanwl
 * @version $Id: ResMsg_AutoRedPacket_RedPacketSend.java, v 0.1 2016-3-4 上午10:50:42 yanwl Exp $
 */
public class ResMsg_AutoRedPacket_RedPacketSend extends ResMsg {

    /**  
     * 序列化编号
     */
    private static final long serialVersionUID = 5251287360520113969L;
    /*红包信息表主键id*/
    private List<Integer> redPacketInfoIds;

   	public List<Integer> getRedPacketInfoIds() {
   		return redPacketInfoIds;
   	}

   	public void setRedPacketInfoIds(List<Integer> redPacketInfoIds) {
   		this.redPacketInfoIds = redPacketInfoIds;
   	}
}
