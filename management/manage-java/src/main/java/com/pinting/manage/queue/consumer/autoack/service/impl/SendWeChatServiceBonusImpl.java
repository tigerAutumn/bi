package com.pinting.manage.queue.consumer.autoack.service.impl;

import com.pinting.business.enums.WeChatTemplateEnum;
import com.pinting.business.model.BsWxUserInfo;
import com.pinting.business.model.vo.WeChat4RabbitModel;
import com.pinting.business.service.site.WxUserService;
import com.pinting.business.util.WxMessageUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.rabbit.queue.core.model.RabbitFlowContext;
import com.pinting.rabbit.queue.core.model.RabbitQueuesVO;
import com.pinting.rabbit.queue.core.service.RabbitServiceExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("sendWeChatServiceBonusImpl")
public class SendWeChatServiceBonusImpl implements RabbitServiceExecutor {

	private Logger logger = LoggerFactory.getLogger(SendWeChatServiceBonusImpl.class);
	@Autowired
	private WxUserService wxUserService;
	
	@Override
	public Object execute(RabbitFlowContext flowContext) {
		logger.info("==============微信模板消息发放，队列处理开始============");
		
		RabbitQueuesVO rabbitQueuesVO =flowContext.getQueuesVO();
		WeChat4RabbitModel weChat4RabbitModel = (WeChat4RabbitModel) rabbitQueuesVO.getBodyVO(WeChat4RabbitModel.class);
		HashMap<String, String> map = weChat4RabbitModel.getMap();
		Integer userId = weChat4RabbitModel.getUserId();
		//获取用户id，查询openId是否存在,存在则组织微信模板发送微信消息
		if(userId == null)return null;
		String openId =  getOpenId(userId);
		if(StringUtil.isNotBlank(openId)){
			try {
				//map中放入openId
				map.put("bgw_open_id", openId);
				String content = WeChatTemplateEnum.getWeChatNoticeEnum(weChat4RabbitModel.getWeChatTempEnumCode()).getNoticeTemp();
		        for (Map.Entry<String, String> entry : map.entrySet()) {
		            content = content.replace(entry.getKey(), entry.getValue());
		        }
		        net.sf.json.JSONObject obj = WxMessageUtil.sendWxMessage(content);
		        if(obj != null){
		        	logger.error("============== 发送微信模板异常："+obj.toString());
		        }
			} catch (Exception e) {
				logger.error("============== 发送微信模板异常：",e);
			}
	        
		}
		logger.info("==============微信模板消息发放，队列处理结束============");
		return null;
	}

	
	/**
	 * 通过userId返回openId
	 * @param userId
	 * @return
	 */
	private String getOpenId(Integer userId) {
		BsWxUserInfo wxUser = wxUserService.getWxUserByUserId(userId);
		if(wxUser != null){
			return wxUser.getOpenId();
		}
		return null;
	}
}
