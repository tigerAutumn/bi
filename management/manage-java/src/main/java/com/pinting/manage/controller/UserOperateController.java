package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppPushMessage_AppPushMessageAdd;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AgentQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_AppMessageQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_Index;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MAppPushMessage_AppPushMessageAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AgentQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_AppMessageQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_Index;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;

/**
 *  细分用户运营
 * @Project: manage-java
 * @Title: UserOperateController.java
 * @author yanwl
 * @date 2016-2-25 下午14:11:58
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
@Controller
@RequestMapping("/user/operate")
public class UserOperateController {
	@Autowired
	@Qualifier("dispatchService")
	private HessianService manageService;

	/**
	 * 细分用户运营首页
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public String userOperateIndex(ReqMsg_MUserOperate_Index req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {

		ResMsg_MUserOperate_Index res = (ResMsg_MUserOperate_Index)manageService.handleMsg(req);
		ResMsg_MUserOperate_AgentQuery resAgent = (ResMsg_MUserOperate_AgentQuery)manageService.handleMsg(new ReqMsg_MUserOperate_AgentQuery());
		ReqMsg_MUserOperate_UserOperateQuery reqUserOperate = new ReqMsg_MUserOperate_UserOperateQuery();
		reqUserOperate.setNonAgentId("0");
		ArrayList<HashMap<String, Object>> list = resAgent.getAgentList();
		String agentIds = "";
		for (HashMap<String, Object> hashMap : list) {
			if(hashMap.get("id") != null) {
				agentIds += hashMap.get("id") + ",";
			}
		}
		reqUserOperate.setAgentIds(agentIds);
		model.put("banks", res.getBankList());
		model.put("req", reqUserOperate);
		model.put("totalRows", 0);
		model.put("agentTotal", list.size());
		return  "/operate/operate_index";
	}
	
	/**
	 * 所有用户渠道
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/all_agent")
	public String allAgent(ReqMsg_MUserOperate_AgentQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MUserOperate_AgentQuery res = (ResMsg_MUserOperate_AgentQuery)manageService.handleMsg(req);
		model.put("agents", res.getAgentList());
		return  "/operate/agent_index";
	}
	
	/**
	 * 用户运营信息查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/search")
	public String operateSearch(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {

		ResMsg_MUserOperate_Index resIndex = (ResMsg_MUserOperate_Index)manageService.handleMsg(new ReqMsg_MUserOperate_Index());
		model.put("banks", resIndex.getBankList());
		
		if(req.getPageNum() <= 0 || req.getNumPerPage() <= 0) {
			req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
			req.setNumPerPage(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		ResMsg_MUserOperate_AgentQuery resAgent = (ResMsg_MUserOperate_AgentQuery)manageService.handleMsg(new ReqMsg_MUserOperate_AgentQuery());
		ArrayList<HashMap<String, Object>> list = resAgent.getAgentList();
		
		model.put("req", req);
		model.put("totalRows", res.getTotalRows());
		model.put("operateList", res.getUserOperateList());
		model.put("agentTotal", list.size());
		return  "/operate/operate_index";
	}
	
	/**
	 * app推送界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/app_push")
	public String appPush(ReqMsg_MUserOperate_UserOperateQuery req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_MUserOperate_UserOperateQuery res = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
		model.put("totalRows", res.getTotalRows());
		
		ResMsg_MUserOperate_AppMessageQuery resAppMsg = (ResMsg_MUserOperate_AppMessageQuery)manageService.handleMsg(new ReqMsg_MUserOperate_AppMessageQuery());
		model.put("messages", resAppMsg.getAppMessageList());
		model.put("pushChar", resAppMsg.getPushChar());
		return  "/operate/app_push";
	}
	
	/**
	 * app推送界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryMessage")
	@ResponseBody
	public  String queryMessage(ReqMsg_MUserOperate_AppMessageQuery req, HashMap<String,Object> model,HttpServletRequest request){
		req.setName(request.getParameter("name"));
		ResMsg_MUserOperate_AppMessageQuery res = (ResMsg_MUserOperate_AppMessageQuery)manageService.handleMsg(req);
		return  res.getPushChar();
	}
	
	/**
	 * 文件播推送测试
	 * 
	 */
	@RequestMapping("/push")
	@ResponseBody
	public void pushCustomizedcast(final ReqMsg_MUserOperate_UserOperateQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		final String name = request.getParameter("name");
		
		new Thread(new Runnable(){
			public void run(){
				req.setPageNum(1);
				req.setNumPerPage(Integer.MAX_VALUE);
				ResMsg_MUserOperate_UserOperateQuery resOperate = (ResMsg_MUserOperate_UserOperateQuery)manageService.handleMsg(req);
				List<HashMap<String, Object>>  userList = resOperate.getUserOperateList();
				if(userList.size() > 0) {
					ReqMsg_MAppNotice_AppNoticeQuery reqNotice = new ReqMsg_MAppNotice_AppNoticeQuery();
					reqNotice.setName(name);
					ResMsg_MAppNotice_AppNoticeQuery res = (ResMsg_MAppNotice_AppNoticeQuery)manageService.handleMsg(reqNotice);
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
						for (HashMap<String, Object> hashMap : userList) {
							userIdList.add(Integer.valueOf(hashMap.get("userId").toString()));
						}
						
						
						//userIdList.add(11093);
						reqMsg.setUserIdList(userIdList);
						
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
						
						reqMsg.setAlert(message.getPushChar());
						reqMsg.setBadge(1);
						
						
						//更新消息状态和推送时间
						message.setIsSend(1);
						message.setPushTime(new Date());
						ReqMsg_MAppNotice_AppNoticeUpdate reqAppNotice = new ReqMsg_MAppNotice_AppNoticeUpdate();
						reqAppNotice.setAppMessage(message);
						ResMsg_MAppNotice_AppNoticeUpdate resAppNotice = (ResMsg_MAppNotice_AppNoticeUpdate)manageService.handleMsg(reqAppNotice);
						
						if(message.getReleasePart() == 1) {
							reqMsg.setAndroid_appkey(GlobEnv.get("bgw.android.app.key").trim());
							reqMsg.setAndroid_appMasterSecret(GlobEnv.get("bgw.android.app.master.secret").trim());
							reqMsg.setIos_appkey(GlobEnv.get("bgw.ios.app.key").trim());
							reqMsg.setIos_appMasterSecret(GlobEnv.get("bgw.ios.app.master.secret").trim());
							
							ResMsg_PushUtil_SendCustomizedcast resMsg = (ResMsg_PushUtil_SendCustomizedcast)manageService.handleMsg(reqMsg);
							
							/*String androidRet = resMsg.getAndroidSendResult();
							JSONObject androidRespJson = new JSONObject(androidRet);
							String androidRespRet = androidRespJson.getString("ret");
							List<Integer> androidList = userIdList;
							if("SUCCESS".equals(androidRespRet)) {
								JSONObject data = androidRespJson.getJSONObject("data");
								List<Integer> missed = new ArrayList<Integer>();
								try {
									JSONArray arr = data.getJSONArray("missed");
									if(arr != null) {
										for (Object object : arr) {
											missed.add(Integer.valueOf(object.toString()));
										}
										androidList.removeAll(missed);
									}
								}catch(Exception e) {
									e.printStackTrace();
								}
								
								int count = androidList.size() % Constants.everyNum == 0 ? androidList.size()/Constants.everyNum : androidList.size()/Constants.everyNum +1;
								for(int j = 0; j < count; j++) {
									StringBuffer buffer = new StringBuffer("insert into bs_app_pushed_message(user_id,message_id,create_time,terminal_type) VALUES");
									for (int i = Constants.everyNum * j; i < Constants.everyNum*(j +1); i++) {
										if(i >= androidList.size()) {
											break;
										}else {
											buffer.append("("+androidList.get(i)+","+message.getId()+",'"+DateUtil.format(new Date())+"',1),");
										}
									}
									ReqMsg_MAppPushMessage_AppPushMessageAdd reqAppPushMessage = new ReqMsg_MAppPushMessage_AppPushMessageAdd();
									reqAppPushMessage.setSql(buffer.toString().substring(0, buffer.toString().length()-1));
									ResMsg_MAppPushMessage_AppPushMessageAdd resAppPushMessage = (ResMsg_MAppPushMessage_AppPushMessageAdd)manageService.handleMsg(reqAppPushMessage);
								}
							}
							
							List<Integer> iosList = userIdList;
							String iosRet = resMsg.getIosSendResult();
							JSONObject iosRespJson = new JSONObject(iosRet);
							String iosRespRet = iosRespJson.getString("ret");
							if("SUCCESS".equals(iosRespRet)) {
								JSONObject data = iosRespJson.getJSONObject("data");
								List<Integer> missed = new ArrayList<Integer>();
								try {
									JSONArray arr = data.getJSONArray("missed");
									if(arr != null) {
										for (Object object : arr) {
											missed.add(Integer.valueOf(object.toString()));
										}
										iosList.removeAll(missed);
									}
								}catch(Exception e) {
									e.printStackTrace();
								}
								
								int count = iosList.size() % Constants.everyNum == 0 ? iosList.size()/Constants.everyNum : iosList.size()/Constants.everyNum +1;
								for(int j = 0; j < count; j++) {
									StringBuffer buffer = new StringBuffer("insert into bs_app_pushed_message(user_id,message_id,create_time,terminal_type) VALUES");
									for (int i = Constants.everyNum * j; i < Constants.everyNum*(j +1); i++) {
										if(i >= iosList.size()) {
											break;
										}else {
											buffer.append("("+androidList.get(i)+","+message.getId()+",'"+DateUtil.format(new Date())+"',2),");
										}
									}
									ReqMsg_MAppPushMessage_AppPushMessageAdd reqAppPushMessage = new ReqMsg_MAppPushMessage_AppPushMessageAdd();
									reqAppPushMessage.setSql(buffer.toString().substring(0, buffer.toString().length()-1));
									ResMsg_MAppPushMessage_AppPushMessageAdd resAppPushMessage = (ResMsg_MAppPushMessage_AppPushMessageAdd)manageService.handleMsg(reqAppPushMessage);
								}
							}
							
							if("SUCCESS".equals(androidRespRet) || "SUCCESS".equals(iosRespRet)) {
								//更新消息状态和推送时间
								message.setIsSend(1);
								message.setPushTime(new Date());
								ReqMsg_MAppNotice_AppNoticeUpdate reqAppNotice = new ReqMsg_MAppNotice_AppNoticeUpdate();
								reqAppNotice.setAppMessage(message);
								ResMsg_MAppNotice_AppNoticeUpdate resAppNotice = (ResMsg_MAppNotice_AppNoticeUpdate)manageService.handleMsg(reqAppNotice);
							}*/
							
							try {
								//添加用户推送消息记录
								int count = userIdList.size() % Constants.everyNum == 0 ? userIdList.size()/Constants.everyNum : userIdList.size()/Constants.everyNum +1;
								for(int j = 0; j < count; j++) {
									StringBuffer buffer = new StringBuffer("insert into bs_app_pushed_message(user_id,message_id,create_time) VALUES");
									for (int i = Constants.everyNum * j; i < Constants.everyNum*(j +1); i++) {
										if(i >= userIdList.size()) {
											break;
										}else {
											buffer.append("("+userIdList.get(i)+","+message.getId()+",'"+DateUtil.format(new Date())+"'),");
										}
									}
									ReqMsg_MAppPushMessage_AppPushMessageAdd reqAppPushMessage = new ReqMsg_MAppPushMessage_AppPushMessageAdd();
									reqAppPushMessage.setSql(buffer.toString().substring(0, buffer.toString().length()-1));
									ResMsg_MAppPushMessage_AppPushMessageAdd resAppPushMessage = (ResMsg_MAppPushMessage_AppPushMessageAdd)manageService.handleMsg(reqAppPushMessage);
								}
							}catch(Exception e){
								e.printStackTrace();
							}
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
		}).start();
	}
}
