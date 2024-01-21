package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsAppMessageMapper;
import com.pinting.business.dao.BsAppPushedMessageMapper;
import com.pinting.business.dao.BsInterestTicketInfoMapper;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.hessian.manage.message.ResMsg_PushUtil_SendCustomizedcast;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.BsInterestTicketInfo;
import com.pinting.business.model.vo.TicketInterestNotifyVO;
import com.pinting.business.service.manage.PushUtilService;
import com.pinting.business.service.site.AppNoticeService;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
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
 * 卡券信息通知用户定时任务
 * Created by zousheng on 2018/4/8.
 */
@Service
public class TicketNotifyTask {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(TicketNotifyTask.class);

    @Autowired
    private BsInterestTicketInfoMapper bsInterestTicketInfoMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private AppNoticeService appNoticeService;

    /**
     * 加息券信息提醒通知
     */
    public void interestTicketNotify() {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.INTEREST_TICKET_NOTIFY.getKey());
            log.info("加息券信息通知提醒开始");
            List<TicketInterestNotifyVO> notifyVOs = bsInterestTicketInfoMapper.selectInterestTicketNotify();
            if (CollectionUtils.isEmpty(notifyVOs)) {
                log.info("加息券提醒通知用户数：0");
                return;
            }
            log.info("加息券提醒通知用户数：" + notifyVOs.size());
            for (TicketInterestNotifyVO notifyVO : notifyVOs) {
                if (StringUtil.isNotBlank(notifyVO.getNotifyChannel())) {
                    String[] notifyChannels = notifyVO.getNotifyChannel().split(",");
                    List<Integer> userIdList = new ArrayList<>();
                    userIdList.add(notifyVO.getUserId());
                    log.info("加息券提现通知用户编号：" + notifyVO.getUserId() + "加息券名称:" + notifyVO.getTicketName() + "通知渠道：" + notifyVO.getNotifyChannel());
                    for (String notifyChannel : notifyChannels) {
                        try {
                            if (Constants.TICKET_NOTIFY_CHANNEL_WECHAT.equals(notifyChannel)) {
                                continue;
//                                sendWechatService.ticketInterestGrant(notifyVO.getUserId(), notifyVO.getTicketName(), notifyVO.getTicketApr());
                            } else if (Constants.TICKET_NOTIFY_CHANNEL_SMS.equals(notifyChannel)) {
                                smsServiceClient.sendTemplateMessage(notifyVO.getMobile(), TemplateKey.MESSAGE_TICKET_GRANT, notifyVO.getTicketApr().toString() + "%", notifyVO.getTicketName());
                            } else if (Constants.TICKET_NOTIFY_CHANNEL_APP.equals(notifyChannel)) {
                            	appNoticeService.sendTicketMessage("领取加息券通知",  "恭喜你获得" + notifyVO.getTicketApr() + "%" + notifyVO.getTicketName() + "。", userIdList);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                // 更新加息券通知状态为 FINISHED 已发送
                BsInterestTicketInfo record = new BsInterestTicketInfo();
                record.setId(notifyVO.getId());
                record.setMsgStatus(Constants.ICKET_MSG_STATUS_FINISHED);
                record.setUpdateTime(new Date());
                bsInterestTicketInfoMapper.updateByPrimaryKeySelective(record);
            }
            log.info("加息券信息通知提醒结束");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.INTEREST_TICKET_NOTIFY.getKey());
        }
    }
}
