/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_FindRedPacketInfoGrid;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.vo.BsRedPacketInfoVO;
import com.pinting.business.service.manage.BsRedPackatInfoService;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

/**
 * 
 * @author HuanXiong
 * @version $Id: BsRedPackatInfoServiceImpl.java, v 0.1 2016-2-29 下午5:57:03 HuanXiong Exp $
 */
@Service
public class BsRedPackatInfoServiceImpl implements BsRedPackatInfoService {

    @Autowired
    private BsRedPacketInfoMapper bsRedPacketInfoMapper;
    @Autowired
	private SMSServiceClient smsServiceClient;
    @Autowired
	private SendWechatService sendWechatService;
    @Autowired
    private PushUtilService pushUtilService;
    @Autowired
    private BsAppMessageMapper appMapper;
    @Autowired
    private BsAppPushedMessageMapper appPushMapper;

    /** 
     * @see com.pinting.business.service.manage.BsRedPackatInfoService#findRedPacketInfoGrid(com.pinting.business.hessian.manage.message.ReqMsg_RedPacketInfo_FindRedPacketInfoGrid, com.pinting.business.hessian.manage.message.ResMsg_RedPacketInfo_FindRedPacketInfoGrid)
     */
    @Override
    public void findRedPacketInfoGrid(ReqMsg_RedPacket_FindRedPacketInfoGrid req,
                                      ResMsg_RedPacket_FindRedPacketInfoGrid res) {
    	//选择查询的渠道列表
    	List<Object> agentIds = null;
    	if(StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] ids = req.getAgentIds().split(",");
			if(ids.length > 0) {
				agentIds = new ArrayList<Object>();
				for (String str : ids) {
					if(StringUtil.isNotEmpty(str)) {
						agentIds.add(Integer.valueOf(str));
					}
				}
			}
		}
    	
    	Integer count = bsRedPacketInfoMapper.countRedPacketInfoGrid(
            req.getUseTimeStart(), req.getUseTimeEnd(), req.getDistributeTimeStart(),
            req.getDistributeTimeEnd(), req.getAgentId(), req.getDistributeType(), req.getTriggerType(), req.getStatus(),
            req.getMobile(), req.getSerialNo(),agentIds,req.getNonAgentId(),
            req.getRedPacketName().replace("_", "/_").replace("%", "/%"),req.getRedPacketName().contains("%") || req.getRedPacketName().contains("_") ? "Y":null,
            req.getUsedTimeStart(),req.getUsedTimeEnd());
        if(count > 0){
            List<BsRedPacketInfoVO> vos = bsRedPacketInfoMapper.selectRedPacketInfoGrid(
                req.getUseTimeStart(), req.getUseTimeEnd(), req.getDistributeTimeStart(),
                req.getDistributeTimeEnd(), req.getAgentId(), req.getDistributeType(), req.getTriggerType(), req.getStatus(),
                req.getMobile(), req.getSerialNo(), req.getStart(), req.getNumPerPage(),agentIds,req.getNonAgentId(),req.getRedPacketName().replace("_", "/_").replace("%", "/%"),req.getRedPacketName().contains("%") || req.getRedPacketName().contains("_") ? "Y":null,
           		req.getUsedTimeStart(),req.getUsedTimeEnd());
            if(!CollectionUtils.isEmpty(vos)) {
                Date now = new Date();
                for (BsRedPacketInfoVO vo : vos) {
                    if(DateUtil.compareTo(vo.getUseTimeEnd(), now) < 0) {
                        if(Constants.RED_PACKET_STATUS_INIT.equals(vo.getStatus())) {
                            vo.setStatus(Constants.RED_PACKET_STATUS_OVER);
                        }
                    }
                }
                res.setDataGrid(BeanUtils.classToArrayList(vos));
            }
        }
        res.setCount(count);
    }

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
	@Override
	public void sendRedPacketMessage(Integer type, List<String> mobiles, List<Integer> userIds, String packetName, String packetAmount) {
		switch(type) {
		case 1:
			for(String mobile : mobiles) {
				try {
					smsServiceClient.sendTemplateMessage(mobile, TemplateKey.RED_PACKET_SEND,packetAmount,packetName);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 2:
			for(Integer userId : userIds) {
				try {
					sendWechatService.packetSend(userId, packetAmount, packetName);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 3:
			try {
				Date now = new Date();
				String time = com.pinting.core.util.DateUtil.format(now);
				//新增app消息表
				BsAppMessage message = new BsAppMessage();
				message.setReleasePart(1); //发送终端为币港湾app
				message.setName("领取币港湾红包！");
				message.setTitle("领取币港湾红包！");
				message.setPushChar("恭喜您获得合计"+packetAmount+"元的"+packetName+"！");
				message.setPushAbstract("恭喜您获得合计"+packetAmount+"元的"+packetName+"！");
				message.setPushType(3); //app内页
				message.setAppPage(15); //红包列表
				message.setIsSend(1);
				message.setCreateTime(now);
				message.setPushTime(now);
				appMapper.insertSelective(message);
				
				//新增app消息推送表
				int count = 500;
				int total = userIds.size()%count==0?userIds.size()/count:userIds.size()/count+1;
				for(int j=0;j<total;j++) {
					StringBuffer sql = new StringBuffer("insert into bs_app_pushed_message(user_id,message_id,create_time) values");
					for(int i=j*count;i<count*(j+1);i++) {
						if(i == userIds.size()) {
							break;
						}
						String temp = "("+userIds.get(i)+","+message.getId()+",'"+time+"'),";
						sql.append(temp);
					}
					appPushMapper.insertBsAppPushedMessage(sql.toString().substring(0,sql.toString().length()-1));
				}
				
				//推送
				final ReqMsg_PushUtil_SendCustomizedcast req = new ReqMsg_PushUtil_SendCustomizedcast();
				final ResMsg_PushUtil_SendCustomizedcast res = new ResMsg_PushUtil_SendCustomizedcast();
				req.setUserIdList(userIds);
				req.setTicker(message.getName());
				req.setTitle(message.getTitle());
				req.setText(message.getPushChar());
				req.setProductionMode(GlobEnvUtil.get("umeng.push.environment").trim().equals("test")?false:true);
				HashMap<String,Object> extendMap = new HashMap<String,Object>();
				extendMap.put("goFlag", 3);
				extendMap.put("appPage", message.getAppPage());
				req.setExtendMap(extendMap);
				req.setAndroid_appkey(GlobEnvUtil.get("bgw.android.app.key"));
				req.setAndroid_appMasterSecret(GlobEnvUtil.get("bgw.android.app.master.secret"));
				req.setIos_appkey(GlobEnvUtil.get("bgw.ios.app.key"));
				req.setIos_appMasterSecret(GlobEnvUtil.get("bgw.ios.app.master.secret"));
				
				//ios的额外配置
				req.setAlert(message.getPushChar());
				req.setBadge(1);
				new Thread(new Runnable() {
					public void run() {
						pushUtilService.sendCustomizedcast(req, res);
					}
				}).start();
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		}
		
	}
	
}
