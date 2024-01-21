package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.dao.BsInterestTicketInfoMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.vo.TicketReminderNotifyVO;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 优惠券（红包、加息券）到期提醒定时
 * 距离有效期倒数6天的发送短信提醒，距离有效期倒数4天的发送微信和APP推送提醒
 *
 * @author shh
 * @date 2018/5/26 16:42
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class TicketReminderNotifyTask {

    private Logger log = LoggerFactory.getLogger(TicketReminderNotifyTask.class);

    @Autowired
    private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private PushUtilService pushUtilService;
    @Autowired
    private BsAppMessageMapper bsAppMessageMapper;
    @Autowired
    private BsAppPushedMessageMapper bsAppPushedMessageMapper;

    public void ticketReminderNotify() {
        try {
            log.info("优惠券到期提醒定时开始");

            Date now = new Date();
            // 距离有效期倒数6天
            String sixDueDate = DateUtil.getDate(DateUtil.nextDay(now, 5));
            // 距离有效期倒数4天
            String fourDueDate = DateUtil.getDate(DateUtil.nextDay(now, 3));

            // 1、距离有效期倒数6天到期的优惠券
            List<TicketReminderNotifyVO> sixTicketList = bsInterestTicketInfoMapper.selectTicketReminderList(sixDueDate);
            // 2、距离有效期倒数4天到期的优惠券
            List<TicketReminderNotifyVO> fourTicketList = bsInterestTicketInfoMapper.selectTicketReminderList(fourDueDate);
            if (CollectionUtils.isEmpty(sixTicketList)) {
                log.info("距离有效期倒数6天的优惠券，通知用户数：0");
            }
            if (CollectionUtils.isEmpty(fourTicketList)) {
                log.info("距离有效期倒数4天的优惠券，通知用户数：0");
                return;
            }

            // 3、距离有效期倒数6天的发送短信提醒
            for(TicketReminderNotifyVO sixTicketVo : sixTicketList) {
                log.info("优惠券距离有效期倒数6天通知的用户编号：" + sixTicketVo.getUserId() + "，手机号码：" + sixTicketVo.getMobile());
                smsServiceClient.sendTemplateMessage(sixTicketVo.getMobile(), TemplateKey.MESSAGE_TICKET_REMINDER_NOTIFY, sixTicketVo.getTicketCount().toString(), sixDueDate);
            }

            // 4、获取推动app消息的userId
            List<Integer> userIdList = new ArrayList<>();
            for(TicketReminderNotifyVO fourTicketVo : fourTicketList) {
                userIdList.add(fourTicketVo.getUserId());
            }

            for(TicketReminderNotifyVO fourTicketVo : fourTicketList) {
                log.info("优惠券距离有效期倒数4天通知的用户编号：" + fourTicketVo.getUserId() + "，手机号码：" + fourTicketVo.getMobile());
                // 5、距离有效期倒数4天的推送微信消息-微信推送消息取消
//                sendWechatService.ticketReminderNotify(fourTicketVo.getUserId(), fourTicketVo.getTicketCount(), fourDueDate);
                // 6、距离有效期倒数4天的推送app消息
                sendTicketMessage("优惠券过期通知",  "您有" + fourTicketVo.getTicketCount() + "个优惠券将于3天后过期（" + fourDueDate + "为最后可用期限），请尽快使用。", userIdList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * app推送信息
     * @param title 标题
     * @param content 内容
     * @param userIds userId集合
     */
    private void sendTicketMessage(String title, String content, List<Integer> userIds) {
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
