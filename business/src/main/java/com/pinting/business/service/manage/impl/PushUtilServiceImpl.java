package com.pinting.business.service.manage.impl;

import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendBroadcast;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendBroadcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.mobile.AndroidNotification;
import com.pinting.business.mobile.PushUtil;
import com.pinting.business.mobile.android.AndroidBroadcast;
import com.pinting.business.mobile.android.AndroidCustomizedcast;
import com.pinting.business.mobile.ios.IOSBroadcast;
import com.pinting.business.mobile.ios.IOSCustomizedcast;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.core.util.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PushUtilServiceImpl implements PushUtilService {

	private Logger log = LoggerFactory.getLogger(PushUtilServiceImpl.class);

	@Override
	public void sendCustomizedcast(ReqMsg_PushUtil_SendCustomizedcast req, ResMsg_PushUtil_SendCustomizedcast res) {
		log.info("自定义播或者文件播推送消息开始");
		if(req.getUserIdList() != null && !req.getUserIdList().isEmpty()) {
			try {
				//android 推送
				AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
				
				if(AndroidNotification.DisplayType.MESSAGE.getValue().equals(req.getDisplayType())) {
					customizedcast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
					try {
						JSONObject json = new JSONObject(req.getCustom());
						customizedcast.goCustomAfterOpen(json);
					}catch(Exception e) {
						customizedcast.goCustomAfterOpen(req.getCustom());
					}
				}else {
					customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
					customizedcast.setTicker(req.getTicker());
					customizedcast.setTitle(req.getTitle());
					customizedcast.setText(req.getText());
					if(StringUtil.isNotEmpty(req.getIcon())) {
						customizedcast.setIcon(req.getIcon());
					}
					if(StringUtil.isNotEmpty(req.getLargeIcon())) {
						customizedcast.setLargeIcon(req.getLargeIcon());
					}
					if(StringUtil.isNotEmpty(req.getImg())) {
						customizedcast.setImg(req.getImg());
					}
					if(StringUtil.isNotEmpty(req.getSound())) {
						customizedcast.setSound(req.getSound());
					}
					if(req.getBuilderId() != null) {
						customizedcast.setBuilderId(req.getBuilderId());
					}
					if(!req.isPlayVibrate()) {
						customizedcast.setPlayVibrate(req.isPlayVibrate());
					}
					if(!req.isPlayLights()) {
						customizedcast.setPlayLights(req.isPlayLights());
					}
					if(!req.isPlaySound()) {
						customizedcast.setPlaySound(req.isPlaySound());
					}
					if(StringUtil.isNotEmpty(req.getUrl())) {
						customizedcast.goUrlAfterOpen(req.getUrl());
					}else if(StringUtil.isNotEmpty(req.getActivity())) {
						customizedcast.goActivityAfterOpen(req.getActivity());
					}else {
						customizedcast.goAppAfterOpen();
					}
					
					//额外的参数
					Map<String,Object> extraMap = req.getExtendMap();
					if(extraMap != null && !extraMap.isEmpty()) {
						for (Map.Entry<String, Object> entry : extraMap.entrySet()) {
							if(StringUtil.isNotEmpty(entry.getKey()) && entry.getValue() != null && StringUtil.isNotEmpty(entry.getValue().toString())) {
								customizedcast.setExtraField(entry.getKey(), entry.getValue().toString());
							}
						}
					}
				}
				if(req.isProductionMode()) {
					customizedcast.setProductionMode();
				}else {
					customizedcast.setTestMode();
				}
				if(StringUtil.isNotEmpty(req.getStartTime())) {
					customizedcast.setStartTime(req.getStartTime());
				}
				if(StringUtil.isNotEmpty(req.getExpireTime())) {
					customizedcast.setExpireTime(req.getExpireTime());
				}
				if(req.getMaxSendNum() != null) {
					customizedcast.setMaxSendNum(req.getMaxSendNum());
				}
				if(StringUtil.isNotEmpty(req.getDescription())) {
					customizedcast.setDescription(req.getDescription());
				}
				List<Integer> list = req.getUserIdList();
				if(list.size() >= 50) {
					StringBuffer alias = new StringBuffer();
					for (int i=0; i<list.size(); i++) {
						if(i == list.size()-1) {
							alias.append(list.get(i));
						}else {
							alias.append(list.get(i));
							alias.append("\n");
						}
					}
//					String fileId = PushUtil.getFileld(alias.toString(),null);
					String fileId = PushUtil.getFileld(alias.toString(),req.getAndroid_appMasterSecret(),req.getAndroid_appkey());
					customizedcast.setFileId(fileId,req.getAndroidAliasType());
				}else {
					StringBuffer alias = new StringBuffer();
					if(list.size() > 1) {
						for (int i=0; i<list.size(); i++) {
							if(i == list.size()-1) {
								alias.append(list.get(i));
							}else {
								alias.append(list.get(i));
								alias.append(",");
							}
						}
					}else {
						alias.append(list.get(0));
					}
					customizedcast.setAlias(alias.toString(), req.getAndroidAliasType());
				}
				log.info("android自定义播或者文件播推送开始:{}", net.sf.json.JSONObject.fromObject(customizedcast));
				String androidSendResult = PushUtil.pushMsg(customizedcast,req.getAndroid_appMasterSecret(),req.getAndroid_appkey());
				res.setAndroidSendResult(androidSendResult);
				log.info("android自定义播或者文件播推送结束:{}", net.sf.json.JSONObject.fromObject(res));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			try {
				//ios 推送
				IOSCustomizedcast iosCustomizedcast = new IOSCustomizedcast();
				iosCustomizedcast.setAlert(req.getAlert());
				if(StringUtil.isNotEmpty(req.getSound())) {
					iosCustomizedcast.setSound(req.getSound());
				}else {
					iosCustomizedcast.setSound("default");
				}
				if(req.getBadge() != null) {
					iosCustomizedcast.setBadge(req.getBadge());
				}
				if(StringUtil.isNotEmpty(req.getStartTime())) {
					iosCustomizedcast.setStartTime(req.getStartTime());
				}
				if(StringUtil.isNotEmpty(req.getExpireTime())) {
					iosCustomizedcast.setExpireTime(req.getExpireTime());
				}
				if(req.getMaxSendNum() != null) {
					iosCustomizedcast.setMaxSendNum(req.getMaxSendNum());
				}
				if(StringUtil.isNotEmpty(req.getDescription())) {
					iosCustomizedcast.setDescription(req.getDescription());
				}
				if(req.isProductionMode()) {
					iosCustomizedcast.setProductionMode();
				}else {
					iosCustomizedcast.setTestMode();
				}
				
				//额外的参数
				Map<String,Object> extraMap = req.getExtendMap();
				if(extraMap != null && !extraMap.isEmpty()) {
					for (Map.Entry<String, Object> entry : extraMap.entrySet()) {
						if(StringUtil.isNotEmpty(entry.getKey()) && entry.getValue() != null && StringUtil.isNotEmpty(entry.getValue().toString())) {
							iosCustomizedcast.setCustomizedField(entry.getKey(), entry.getValue().toString());
						}
					}
				}
				
				List<Integer> list = req.getUserIdList();
				if(list.size() >= 50) {
					StringBuffer alias = new StringBuffer();
					for (int i=0; i<list.size(); i++) {
						if(i == list.size()-1) {
							alias.append(list.get(i));
						}else {
							alias.append(list.get(i));
							alias.append("\n");
						}
					}
					
//					String fileId = PushUtil.getFileld(alias.toString(),"ios");
					String fileId = PushUtil.getFileld(alias.toString(),req.getIos_appMasterSecret(),req.getIos_appkey());
					iosCustomizedcast.setFileId(fileId,req.getIosAliasType());
				}else {
					StringBuffer alias = new StringBuffer();
					if(list.size() > 1) {
						for (int i=0; i<list.size(); i++) {
							if(i == list.size()-1) {
								alias.append(list.get(i));
							}else {
								alias.append(list.get(i));
								alias.append(",");
							}
						}
					}else {
						alias.append(list.get(0));
					}
					iosCustomizedcast.setAlias(alias.toString(), req.getIosAliasType());
				}
				log.info("ios自定义播或者文件播推送开始:{}", net.sf.json.JSONObject.fromObject(iosCustomizedcast));
				String iosSendResult = PushUtil.pushMsg(iosCustomizedcast,req.getIos_appMasterSecret(),req.getIos_appkey());
				res.setIosSendResult(iosSendResult);
				log.info("ios自定义播或者文件播推送结束:{}", net.sf.json.JSONObject.fromObject(res));
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		log.info("自定义播或者文件播推送消息结束");

	}

	@Override
	public void sendBroadcast(ReqMsg_PushUtil_SendBroadcast req, ResMsg_PushUtil_SendBroadcast res) {
		log.info("广播推送消息开始");
		try {
			//android广播推送
			AndroidBroadcast broadcast = new AndroidBroadcast();
			if(AndroidNotification.DisplayType.MESSAGE.getValue().equals(req.getDisplayType())) {
				broadcast.setDisplayType(AndroidNotification.DisplayType.MESSAGE);
				try {
					JSONObject json = new JSONObject(req.getCustom());
					broadcast.goCustomAfterOpen(json);
				}catch(Exception e) {
					broadcast.goCustomAfterOpen(req.getCustom());
				}
			}else {
				broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
				broadcast.setTicker(req.getTicker());
				broadcast.setTitle(req.getTitle());
				broadcast.setText(req.getText());
				if(StringUtil.isNotEmpty(req.getIcon())) {
					broadcast.setIcon(req.getIcon());
				}
				if(StringUtil.isNotEmpty(req.getLargeIcon())) {
					broadcast.setLargeIcon(req.getLargeIcon());
				}
				if(StringUtil.isNotEmpty(req.getImg())) {
					broadcast.setImg(req.getImg());
				}
				if(StringUtil.isNotEmpty(req.getSound())) {
					broadcast.setSound(req.getSound());
				}
				if(req.getBuilderId() != null) {
					broadcast.setBuilderId(req.getBuilderId());
				}
				if(!req.isPlayVibrate()) {
					broadcast.setPlayVibrate(req.isPlayVibrate());
				}
				if(!req.isPlayLights()) {
					broadcast.setPlayLights(req.isPlayLights());
				}
				if(!req.isPlaySound()) {
					broadcast.setPlaySound(req.isPlaySound());
				}
				if(StringUtil.isNotEmpty(req.getUrl())) {
					broadcast.goUrlAfterOpen(req.getUrl());
				}else if(StringUtil.isNotEmpty(req.getActivity())) {
					broadcast.goActivityAfterOpen(req.getActivity());
				}else {
					broadcast.goAppAfterOpen();
				}
				
				//额外的参数
				Map<String,Object> extraMap = req.getExtendMap();
				if(extraMap != null && !extraMap.isEmpty()) {
					for (Map.Entry<String, Object> entry : extraMap.entrySet()) {
						if(StringUtil.isNotEmpty(entry.getKey()) && entry.getValue() != null && StringUtil.isNotEmpty(entry.getValue().toString())) {
							broadcast.setExtraField(entry.getKey(), entry.getValue().toString());
						}
					}
				}
			}
			if(req.isProductionMode()) {
				broadcast.setProductionMode();
			}else {
				broadcast.setTestMode();
			}
			if(StringUtil.isNotEmpty(req.getStartTime())) {
				broadcast.setStartTime(req.getStartTime());
			}
			if(StringUtil.isNotEmpty(req.getExpireTime())) {
				broadcast.setExpireTime(req.getExpireTime());
			}
			if(req.getMaxSendNum() != null) {
				broadcast.setMaxSendNum(req.getMaxSendNum());
			}
			if(StringUtil.isNotEmpty(req.getDescription())) {
				broadcast.setDescription(req.getDescription());
			}
			log.info("android广播推送开始");
//			PushUtil.pushMsg(broadcast,null);
			log.info("android广播推送结束");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//ios广播推送
		try {
			IOSBroadcast iosBroadcast = new IOSBroadcast();
			iosBroadcast.setAlert(req.getAlert());
			if(StringUtil.isNotEmpty(req.getSound())) {
				iosBroadcast.setSound(req.getSound());
			}else {
				iosBroadcast.setSound("default");
			}
			if(req.getBadge() != null) {
				iosBroadcast.setBadge(req.getBadge());
			}
			if(StringUtil.isNotEmpty(req.getStartTime())) {
				iosBroadcast.setStartTime(req.getStartTime());
			}
			if(StringUtil.isNotEmpty(req.getExpireTime())) {
				iosBroadcast.setExpireTime(req.getExpireTime());
			}
			if(req.getMaxSendNum() != null) {
				iosBroadcast.setMaxSendNum(req.getMaxSendNum());
			}
			if(StringUtil.isNotEmpty(req.getDescription())) {
				iosBroadcast.setDescription(req.getDescription());
			}
			if(req.isProductionMode()) {
				iosBroadcast.setProductionMode();
			}else {
				iosBroadcast.setTestMode();
			}
			
			//额外的参数
			Map<String,Object> extraMap = req.getExtendMap();
			if(extraMap != null && !extraMap.isEmpty()) {
				for (Map.Entry<String, Object> entry : extraMap.entrySet()) {
					if(StringUtil.isNotEmpty(entry.getKey()) && entry.getValue() != null && StringUtil.isNotEmpty(entry.getValue().toString())) {
						iosBroadcast.setCustomizedField(entry.getKey(), entry.getValue().toString());
					}
				}
			}
			log.info("ios广播推送开始");
//			PushUtil.pushMsg(iosBroadcast, "ios");
			log.info("ios广播推送结束");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		log.info("广播推送消息开始");

	}

}
