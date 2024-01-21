package com.pinting.business.facade.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendBroadcast;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendBroadcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.service.manage.PushUtilService;


/**
 * 推送工具类
 * @Project: manage
 * @Title: PushUtilFacade.java
 * @author yanwl
 * @date 2016-01-19 上午10:39:39
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Component("PushUtil")
public class PushUtilFacade {
	
	@Autowired
	private PushUtilService pushUtilService;
	
	//自定义播或者文件播推送
	public void sendCustomizedcast(ReqMsg_PushUtil_SendCustomizedcast req,ResMsg_PushUtil_SendCustomizedcast res) {
		pushUtilService.sendCustomizedcast(req, res);
	}
	
	//广播(向安装该App的所有设备发送消息)
	public void sendBroadcast(ReqMsg_PushUtil_SendBroadcast req,ResMsg_PushUtil_SendBroadcast res){
		pushUtilService.sendBroadcast(req, res);
	}
}
