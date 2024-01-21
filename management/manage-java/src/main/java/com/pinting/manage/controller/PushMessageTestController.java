package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeQuery;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;

@Controller
public class PushMessageTestController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;
	
	/**
	 * 文件播推送测试
	 * 
	 */
	@RequestMapping("/push/index")
	@ResponseBody
	public void pushCustomizedcast(ReqMsg_MAppNotice_AppNoticeQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		
		req.setName("测试2");
		ResMsg_MAppNotice_AppNoticeQuery res = (ResMsg_MAppNotice_AppNoticeQuery)manageService.handleMsg(req);
		BsAppMessage message = res.getAppMessage();
		if(message != null) {
			ReqMsg_PushUtil_SendCustomizedcast reqMsg = new ReqMsg_PushUtil_SendCustomizedcast();
			
			reqMsg.setTicker(message.getName());
			reqMsg.setTitle(message.getTitle());
			reqMsg.setText(message.getPushChar());
			//req.setDisplayType("notification");
			if(Constants.IS_PUSH_TEST.equals(GlobEnv.get("umeng.push.environment").trim())) {
				reqMsg.setProductionMode(false);
			}else {
				reqMsg.setProductionMode(true);
			}
			
			List<Integer> userIdList = new ArrayList<Integer>();
			//userIdList.add(1);
			//userIdList.add(2000093);
			userIdList.add(1816);
			//userIdList.add(9296);
			//userIdList.add(2);
			
			reqMsg.setUserIdList(userIdList);
			//req.setAndroidAliasType("CoinHarbour");
			
			HashMap<String,Object> extendMap = new HashMap<String,Object>();
			if(message.getPushType() == 3) {
				extendMap.put("goFlag", 3);
				extendMap.put("appPage", message.getAppPage());
			}else if(message.getPushType() == 2) {
				extendMap.put("goFlag", 2);
			}else {
				extendMap.put("goFlag", 1);
				extendMap.put("url", message.getContent());
				extendMap.put("title", message.getTitle());
			}
			reqMsg.setExtendMap(extendMap);
			
			//req.setIosAliasType("");
			reqMsg.setAlert(message.getPushChar());
			reqMsg.setBadge(1);
			
			if(message.getReleasePart() == 1) {
				reqMsg.setAndroid_appkey(GlobEnv.get("bgw.android.app.key").trim());
				reqMsg.setAndroid_appMasterSecret(GlobEnv.get("bgw.android.app.master.secret").trim());
				reqMsg.setIos_appkey(GlobEnv.get("bgw.ios.app.key").trim());
				reqMsg.setIos_appMasterSecret(GlobEnv.get("bgw.ios.app.master.secret").trim());
				
				ResMsg_PushUtil_SendCustomizedcast resMsg = (ResMsg_PushUtil_SendCustomizedcast)manageService.handleMsg(reqMsg);
			}else if(message.getReleasePart() == 2) {
				/*reqMsg.setAndroid_appkey(Constants.qbbgw_android_appkey);
				reqMsg.setAndroid_appMasterSecret(Constants.qbbgw_android_appMasterSecret);
				reqMsg.setIos_appkey(Constants.qbbgw_ios_appkey);
				reqMsg.setIos_appMasterSecret(Constants.qbbgw_ios_appMasterSecret);
				
				ResMsg_PushUtil_SendCustomizedcast resMsg = (ResMsg_PushUtil_SendCustomizedcast)manageService.handleMsg(reqMsg);*/
			}else {
				/*reqMsg.setAndroid_appkey(GlobEnv.get("bgw.android.app.key").trim());
				reqMsg.setAndroid_appMasterSecret(GlobEnv.get("bgw.android.app.master.secret").trim());
				reqMsg.setIos_appkey(GlobEnv.get("bgw.ios.app.key").trim());
				reqMsg.setIos_appMasterSecret(GlobEnv.get("bgw.ios.app.master.secret").trim());
				
				ResMsg_PushUtil_SendCustomizedcast resMsg = (ResMsg_PushUtil_SendCustomizedcast)manageService.handleMsg(reqMsg);
				
				reqMsg.setAndroid_appkey(Constants.qbbgw_android_appkey);
				reqMsg.setAndroid_appMasterSecret(Constants.qbbgw_android_appMasterSecret);
				reqMsg.setIos_appkey(Constants.qbbgw_ios_appkey);
				reqMsg.setIos_appMasterSecret(Constants.qbbgw_ios_appMasterSecret);
				
				ResMsg_PushUtil_SendCustomizedcast resMsg1 = (ResMsg_PushUtil_SendCustomizedcast)manageService.handleMsg(reqMsg);*/
			}
		}
		
	}
}
