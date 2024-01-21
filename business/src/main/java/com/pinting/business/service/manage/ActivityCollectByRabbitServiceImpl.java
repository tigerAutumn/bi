package com.pinting.business.service.manage;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.BsProduct;
import com.pinting.business.service.manage.impl.ActivityCollectByRabbitService;
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

@Service
public class ActivityCollectByRabbitServiceImpl implements ActivityCollectByRabbitService {

	private static Logger logger = LoggerFactory.getLogger(ActivityCollectByRabbitServiceImpl.class);
	@Autowired
	private RabbitProducer rabbitProducer;
	
	@Override
	public void salaryPlanCollect(ReqMsg_RegularBuy_BalanceBuy req, BsProduct product, Integer subAccountId, String bizOrderNo) {
		
		if( subAccountId == null ) {
			logger.info("用户"+req.getUserId()+"购买失败或信息不全,subAccountId为null");
			return;
		}
		JSONObject json = new JSONObject();
		json.put("RegularBuyBalanceReq", req);
		json.put("BsProduct",	product);
		json.put("sub_account_id", subAccountId);
		json.put("order_no", bizOrderNo);
		logger.info("运营日常活动数据采集入MQ=["+json.toJSONString()+"]");
		//加薪计划运营数据采集
		RabbitQueuesVO<JSONObject> rabbitQueues = new RabbitQueuesVO<>();
		rabbitQueues.setQueuesNO(SerialNumberUtil.generateQueueNo(QueueConstant.QUEUENO_PREFIX_JXJH));
		rabbitQueues.setBodyVO(json);
		rabbitQueues.setRabbitBusinessCode(RabbitBusinessEnum.ACTIVITY_COLLECT.getCode());
		rabbitQueues.setRabbitEventCode(RabbitEventEnum.BIZ_BALANCE_BUY.getCode());
		rabbitProducer.enRabbitQueue(rabbitQueues, RabbitBindingEnum.BIZ_ACTIVITY_COLLECT);
	}
}