package com.pinting.business.service.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendBroadcast;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendBroadcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;

import java.util.List;

public interface PushUtilService {

	public void sendCustomizedcast(ReqMsg_PushUtil_SendCustomizedcast req,ResMsg_PushUtil_SendCustomizedcast res);
	
	public void sendBroadcast(ReqMsg_PushUtil_SendBroadcast req,ResMsg_PushUtil_SendBroadcast res);
}
