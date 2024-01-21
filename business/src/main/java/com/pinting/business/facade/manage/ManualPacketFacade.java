package com.pinting.business.facade.manage;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_ManualPacket_ManualPacketAdd;
import com.pinting.business.hessian.manage.message.ResMsg_ManualPacket_ManualPacketAdd;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.service.manage.BsRedPacketCheckService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;


/**
 * 
 * @Project: business
 * @Title: ManualPacketFacade.java
 * @author yanwl
 * @date 2016-03-03 下午13:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("ManualPacket")
public class ManualPacketFacade{
	@Autowired
	private BsRedPacketCheckService redPacketCheckService;
	
	public void manualPacketAdd(ReqMsg_ManualPacket_ManualPacketAdd reqMsg,ResMsg_ManualPacket_ManualPacketAdd resMsg) {
		String serialNo = Util.getSerialNo();
		BsRedPacketCheck redPacketCheck = new BsRedPacketCheck();
		redPacketCheck.setAmount(reqMsg.getSubtract());
		redPacketCheck.setApplicant(reqMsg.getApplicant());
		redPacketCheck.setApplyNo(reqMsg.getApplyNo());
		redPacketCheck.setCheckStatus(Constants.RED_PACKET_CHECK_STATUS_UNCHECKED);
		redPacketCheck.setCreateTime(new Date());
		redPacketCheck.setDistributeType(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_MANUAL);
		redPacketCheck.setMsgStatus(Constants.RED_PACKET_MSG_STATUS_NOT);
		redPacketCheck.setFull(reqMsg.getFull());
		redPacketCheck.setNote(reqMsg.getNote());
		if(StringUtil.isNotEmpty(reqMsg.getNotifyChannel()) && reqMsg.getNotifyChannel().length() >= 2) {
			redPacketCheck.setNotifyChannel(reqMsg.getNotifyChannel().substring(0, reqMsg.getNotifyChannel().length()-1));
		}
		redPacketCheck.setSerialName(reqMsg.getSerialName());
		redPacketCheck.setSerialNo(serialNo);
		redPacketCheck.setSubtract(reqMsg.getSubtract());
		redPacketCheck.setTotal(reqMsg.getTotal());
		redPacketCheck.setUpdateTime(new Date());
		redPacketCheck.setUseTimeStart(DateUtil.parseDateTime(reqMsg.getUseTimeStart()));
		redPacketCheck.setUseTimeEnd(DateUtil.parseDateTime(reqMsg.getUseTimeEnd()));
		redPacketCheck.setUseCondition(reqMsg.getUseCondition());
		redPacketCheck.setManualConditions(reqMsg.getManualConditions());
		if(StringUtil.isNotEmpty(reqMsg.getTermLimit()) && reqMsg.getTermLimit().length() >= 1) {
			redPacketCheck.setTermLimit(reqMsg.getTermLimit().substring(0, reqMsg.getTermLimit().length()-1));
		}
		redPacketCheck.setPolicyType(reqMsg.getPolicyType());
		
		redPacketCheckService.saveRedPacketCheck(redPacketCheck,reqMsg.getUserIdList());
	}
}
