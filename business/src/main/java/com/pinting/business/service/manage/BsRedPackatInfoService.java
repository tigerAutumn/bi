/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_FindRedPacketInfoGrid;

/**
 * 
 * @author HuanXiong
 * @version $Id: BsRedPackatInfoService.java, v 0.1 2016-2-29 下午5:54:08 HuanXiong Exp $
 */
public interface BsRedPackatInfoService {
    
    void findRedPacketInfoGrid(ReqMsg_RedPacket_FindRedPacketInfoGrid req, ResMsg_RedPacket_FindRedPacketInfoGrid res);
    
    /**
     * 
     * @Title: sendRedPacketMessage 
     * @Description: 红包消息推送服务
     * @param type 1、短信2、微信3、app推送
     * @param mobiles 手机号（当微信和app推送时，传入null）
     * @param userIds 用户ID（当短信推送时，传入null）
     * @param packetName 红包名称
     * @param packetAmount 红包金额
     * @throws
     */
    void sendRedPacketMessage(Integer type, List<String> mobiles, List<Integer> userIds, String packetName, String packetAmount);
}
