package com.pinting.business.facade.manage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MAppPushMessage_AppPushMessageAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MAppPushMessage_AppPushMessageAdd;
import com.pinting.business.model.BsAppPushedMessage;
import com.pinting.business.service.manage.MAppPushMessageService;

/**
 * 
 * @Project: business
 * @Title: MAppPushMessageFacade.java
 * @author yanwl
 * @date 2016-02-29 下午13:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MAppPushMessage")
public class MAppPushMessageFacade{
	@Autowired
	private MAppPushMessageService mAppPushMessageService;
	
	
	public void appPushMessageAdd(ReqMsg_MAppPushMessage_AppPushMessageAdd reqMsg,ResMsg_MAppPushMessage_AppPushMessageAdd resMsg) {
		/*BsAppPushedMessage pushMessage = new BsAppPushedMessage();
		pushMessage.setCreateTime(new Date());
		pushMessage.setUserId(reqMsg.getUserId());
		pushMessage.setMessageId(reqMsg.getMessageId());
		pushMessage.setTerminalType(reqMsg.getTerminalType());
		mAppPushMessageService.saveAppPushedMessage(pushMessage);*/
		mAppPushMessageService.saveAppPushedMessage(reqMsg.getSql());
	}
}
