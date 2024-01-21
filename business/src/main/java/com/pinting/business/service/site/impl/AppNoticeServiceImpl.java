package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.business.service.site.AppNoticeService;
import com.pinting.core.util.GlobEnvUtil;

@Service
public class AppNoticeServiceImpl implements AppNoticeService {

	@Autowired
    private PushUtilService pushUtilService;
    @Autowired
    private BsAppMessageMapper bsAppMessageMapper;
    @Autowired
    private BsAppPushedMessageMapper bsAppPushedMessageMapper;
    
	@Override
	public void sendTicketMessage(String title, String content,
			List<Integer> userIds) {
		try {
            Date now = new Date();
            String time = com.pinting.core.util.DateUtil.format(now);
            //新增app消息表
            BsAppMessage message = new BsAppMessage();
            message.setReleasePart(1); //发送终端为币港湾app
            message.setName(title);
            message.setTitle(title);
            message.setPushChar(content);
            message.setPushAbstract(content);
            message.setContent(content);
            message.setPushType(2);
            //message.setAppPage(15);
            message.setIsSend(1);
            message.setCreateTime(now);
            message.setPushTime(now);
            bsAppMessageMapper.insertSelective(message);

            //新增app消息推送表
            int count = 500;
            int total = userIds.size() % count == 0 ? userIds.size() / count : userIds.size() / count + 1;
            for (int j = 0; j < total; j++) {
                StringBuffer sql = new StringBuffer("insert into bs_app_pushed_message(user_id,message_id,create_time) values");
                for (int i = j * count; i < count * (j + 1); i++) {
                    if (i == userIds.size()) {
                        break;
                    }
                    String temp = "(" + userIds.get(i) + "," + message.getId() + ",'" + time + "'),";
                    sql.append(temp);
                }
                bsAppPushedMessageMapper.insertBsAppPushedMessage(sql.toString().substring(0, sql.toString().length() - 1));
            }

            //推送
            final ReqMsg_PushUtil_SendCustomizedcast req = new ReqMsg_PushUtil_SendCustomizedcast();
            final ResMsg_PushUtil_SendCustomizedcast res = new ResMsg_PushUtil_SendCustomizedcast();
            req.setUserIdList(userIds);
            req.setTicker(message.getName());
            req.setTitle(message.getTitle());
            req.setText(message.getPushChar());
            req.setProductionMode(GlobEnvUtil.get("umeng.push.environment").trim().equals("test") ? false : true);
            HashMap<String, Object> extendMap = new HashMap<>();
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
            pushUtilService.sendCustomizedcast(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	}

}
