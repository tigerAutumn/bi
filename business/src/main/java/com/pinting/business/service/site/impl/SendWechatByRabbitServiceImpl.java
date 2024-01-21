package com.pinting.business.service.site.impl;

import com.pinting.business.enums.WeChatTemplateEnum;
import com.pinting.business.model.vo.WeChat4RabbitModel;
import com.pinting.business.service.site.SendWechatByRabbitService;
import com.pinting.core.util.StringUtil;
import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.enums.RabbitBusinessEnum;
import com.pinting.rabbit.queue.core.enums.RabbitEventEnum;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import com.pinting.rabbit.queue.core.util.QueueConstant;
import com.pinting.rabbit.queue.producer.service.RabbitProducer;
import com.pinting.rabbit.util.SerialNumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class SendWechatByRabbitServiceImpl implements SendWechatByRabbitService {

	private static Logger log = LoggerFactory.getLogger(SendWechatByRabbitServiceImpl.class);
	@Autowired
	private RabbitProducer rabbitProducer;
	
	@Override
	public void bonusArrive(Integer userId, String amount, String finishTime) {
		if(userId == null || StringUtil.isBlank(amount) || StringUtil.isBlank(finishTime)){
			log.info("==========发送奖励金到账的微信通知，信息不全，不发送===");
			return;
		}

		HashMap<String, String> map = new HashMap<String, String>();
		//map的key值和WeChatNoticeEnum.BONUS_ARRIVE的模板中的参数对应，userId后续会去查得openId，以bgw_open_id代入
		map.put("bgw_income_amount",amount);
		map.put("bgw_income_time",finishTime);
		//组织微信模板入参
		WeChat4RabbitModel weChat4RabbitModel = new WeChat4RabbitModel();
		weChat4RabbitModel.setUserId(userId);
		weChat4RabbitModel.setMap(map);
		weChat4RabbitModel.setWeChatTempEnumCode(WeChatTemplateEnum.BONUS_ARRIVE.getNoticeCode());
		//组织rabbit生产者入参
		//发送奖励金到账的微信通知
		RabbitQueuesVO<WeChat4RabbitModel> rabbitQueues = new RabbitQueuesVO<>();
		rabbitQueues.setQueuesNO(SerialNumberUtil.generateQueueNo(QueueConstant.QUEUENO_PREFIX_WXTZ));
		rabbitQueues.setBodyVO(weChat4RabbitModel);
		rabbitQueues.setRabbitBusinessCode(RabbitBusinessEnum.WECHAT_MSG_SEND.getCode());
		rabbitQueues.setRabbitEventCode(RabbitEventEnum.SCH_BONUS_GRANT.getCode());
		rabbitProducer.enRabbitQueue(rabbitQueues, RabbitBindingEnum.BIZ_WECHAT_MSG_TEMPLATE);
	}

}
