package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.dao.BsInterestTicketInfoMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.vo.BsInterestTicketManageVO;
import com.pinting.business.service.manage.BsTicketInterestService;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;

/**
 *
 * @author SHENGP
 * @date  2018年4月4日 下午1:44:44
 */
@Service
public class BsTicketInterestServiceImpl implements BsTicketInterestService {

	@Autowired
	private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
    private BsAppMessageMapper appMapper;
	@Autowired
    private BsAppPushedMessageMapper appPushMapper;
	@Autowired
    private PushUtilService pushUtilService;
	
	@Override
	public Integer countTicketInterestInfo(BsInterestTicketManageVO record) {
		return bsInterestTicketInfoMapper.countTicketInterestInfo(record);
	}

	@Override
	public List<BsInterestTicketManageVO> selectTicketInterestInfoList(BsInterestTicketManageVO record) {
		List<BsInterestTicketManageVO> list = bsInterestTicketInfoMapper.selectTicketInterestInfoList(record);
		return CollectionUtils.isEmpty(list) ? null : list; 
	}

	@Override
	public Double sumTicketInterest(BsInterestTicketManageVO record) {
		return bsInterestTicketInfoMapper.sumTicketInterest(record);
	}

	@Override
	public void sendTicketMessage(Integer type, List<String> mobiles,
			List<Integer> userIds, String ticketName, String ticketAPr) {
		switch(type) {
		case 1:
			for(String mobile : mobiles) {
				try {
					smsServiceClient.sendTemplateMessage(mobile, TemplateKey.MESSAGE_TICKET_GRANT,ticketAPr,ticketName);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 2:
			break;
		case 3:
			try {
				Date now = new Date();
				String time = com.pinting.core.util.DateUtil.format(now);
				//新增app消息表
				BsAppMessage message = new BsAppMessage();
				message.setReleasePart(1); //发送终端为币港湾app
				message.setName("领取币港湾加息券！");
				message.setTitle("领取币港湾加息券！");
				message.setPushChar("恭喜您获得利率"+ticketAPr+"元的"+ticketName+"！");
				message.setPushAbstract("恭喜您获得利率"+ticketAPr+"元的"+ticketName+"！");
				message.setPushType(3); //app内页
				message.setAppPage(15); //加息券列表
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
