package com.pinting.business.test;

import java.util.HashMap;
import java.util.Map;

import com.pinting.business.enums.WeChatTemplateEnum;
import com.pinting.business.model.vo.WeChat4RabbitModel;
import com.pinting.rabbit.queue.core.enums.RabbitBindingEnum;
import com.pinting.rabbit.queue.core.enums.RabbitBusinessEnum;
import com.pinting.rabbit.queue.core.enums.RabbitEventEnum;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import com.pinting.rabbit.queue.core.util.QueueConstant;
import com.pinting.rabbit.queue.producer.service.RabbitProducer;
import com.pinting.rabbit.util.SerialNumberUtil;
import net.sf.json.JSONObject;

import com.pinting.business.util.WxTemplate;
import com.pinting.business.util.WxTemplateData;
import org.junit.Test;
//import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 生成微信模板的字符串
 * @project business
 * @author bianyatian
 * @2018-8-9 下午7:21:43
 */
public class WeChatTempTest extends TestBase {

//	@Autowired
//	private RabbitProducer rabbitProducer;

	public static void main(String []args){
		WxTemplate template = new WxTemplate();
		template.setTouser("userId");
		// 标题--奖励金已到账通知
		template.setTemplate_id("bonusArrive");
		template.setUrl("url");
		Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
		data.put("first", new WxTemplateData("尊敬的客户，您有一笔奖励金已到账。", "#173177"));
		data.put("income_amount", new WxTemplateData("income_amount元", "#173177"));
		data.put("income_time", new WxTemplateData("income_time", "#173177"));
		data.put("remark", new WxTemplateData("备注：如有疑问请拨打400-806-1230。", "#173177"));
		template.setData(data);
		//JSONObject obj = WxMessageUtil.sendWxMessage(template);

		String json = JSONObject.fromObject(template).toString();
		System.out.println(json);//需要将"替换成\"


	}
//
//	@Test
//	public void onMessage() {
//		HashMap<String, String> map = new HashMap<String, String>();
//		//map的key值和WeChatNoticeEnum.BONUS_ARRIVE的模板中的参数对应，userId后续会去查得openId，以bgw_open_id代入
//		map.put("bgw_income_amount", "0d");
//		map.put("bgw_income_time", "123456");
//		//组织微信模板入参
//		WeChat4RabbitModel weChat4RabbitModel = new WeChat4RabbitModel();
//		weChat4RabbitModel.setUserId(1);
//		weChat4RabbitModel.setMap(map);
//		weChat4RabbitModel.setWeChatTempEnumCode(WeChatTemplateEnum.BONUS_ARRIVE.getNoticeCode());
//		//组织rabbit生产者入参
//		//发送奖励金到账的微信通知
//		RabbitQueuesVO<WeChat4RabbitModel> rabbitQueues = new RabbitQueuesVO<>();
//		rabbitQueues.setQueuesNO(SerialNumberUtil.generateQueueNo(QueueConstant.QUEUENO_PREFIX_WXTZ));
//		rabbitQueues.setBodyVO(weChat4RabbitModel);
//		rabbitQueues.setRabbitBusinessCode(RabbitBusinessEnum.WECHAT_MSG_SEND.getCode());
//		rabbitQueues.setRabbitEventCode(RabbitEventEnum.SCH_BONUS_GRANT.getCode());
//		rabbitProducer.enRabbitQueue(rabbitQueues, RabbitBindingEnum.BIZ_WECHAT_MSG_TEMPLATE);
//	}

}
