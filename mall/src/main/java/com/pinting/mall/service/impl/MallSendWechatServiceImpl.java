package com.pinting.mall.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.model.MallBsWxUserInfo;
import com.pinting.mall.service.MallSendWechatService;
import com.pinting.mall.service.MallWxUserService;
import com.pinting.mall.util.DateUtil;
import com.pinting.mall.util.WxMessageUtil;
import com.pinting.mall.util.WxTemplate;
import com.pinting.mall.util.WxTemplateData;

import net.sf.json.JSONObject;

@Service
public class MallSendWechatServiceImpl implements MallSendWechatService {
	private static Logger log = LoggerFactory.getLogger(MallSendWechatServiceImpl.class);
	private static String mallPointsUrl = GlobEnvUtil.get("wechat.mallpoints.url");
	String pointsDistribution = GlobEnvUtil.get("wechat.template.pointsDistribution");
	String pointsConsume = GlobEnvUtil.get("wechat.template.pointsConsume");
	@Autowired
	private MallWxUserService wxUserService;
	
	/**
	 * 通过userId返回openId
	 * @param userId
	 * @return
	 */
	private String getOpenId(Integer userId) {
		MallBsWxUserInfo wxUser = wxUserService.getWxUserByUserId(userId);
		if(wxUser != null){
			return wxUser.getOpenId();
		}
		return null;
	}
	
	@Override
	public String mallPointsDistribution(Integer userId, String userName, Date time, String type, Long points, Long balance) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				//设置积分发放消息模板id
				template.setTemplate_id(pointsDistribution);
				//详情跳转链接
				template.setUrl(mallPointsUrl);
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的用户，您好，您的积分已经发送到您的账户，请查收", "#173177"));
				data.put("keyword1", new WxTemplateData(userName, "#173177"));
				data.put("keyword2", new WxTemplateData(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"), "#173177"));
				data.put("keyword3", new WxTemplateData(type, "#173177"));
				data.put("keyword4", new WxTemplateData(points+"", "#173177"));
				data.put("keyword5", new WxTemplateData(balance+"", "#173177"));
				data.put("remark", new WxTemplateData("点击查看账户详情！", "#173177"));
				template.setData(data);
				JSONObject obj = WxMessageUtil.sendWxMessage(template);
				if(obj != null){
					log.error(obj.toString());
					return obj.get("errcode").toString();	
				}else{
					return null;
				}
			}else{
				return "该用户无openId";
			}
		}catch (Exception e){
			log.error("支付微信模板消息异常", e);
			return null;
		}
		
	}

	@Override
	public String mallPointsConsume(Integer userId, String content, String points,String merchantName, Date time) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				//设置积分消费消息模板id
				template.setTemplate_id(pointsConsume);
				//详情跳转链接
				template.setUrl(mallPointsUrl);
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的用户，您好，您刚刚使用了积分兑换了商品", "#173177"));
				data.put("keyword1", new WxTemplateData(content, "#173177"));
				data.put("keyword2", new WxTemplateData(points, "#173177"));
				data.put("keyword3", new WxTemplateData(merchantName, "#173177"));
				data.put("keyword4", new WxTemplateData(DateUtil.format(time, "yyyy-MM-dd HH:mm:ss"), "#173177"));
				data.put("remark", new WxTemplateData("感谢您的使用！", "#173177"));
				template.setData(data);
				JSONObject obj = WxMessageUtil.sendWxMessage(template);
				if(obj != null){
					log.error(obj.toString());
					return obj.get("errcode").toString();	
				}else{
					return null;
				}
			}else{
				return "该用户无openId";
			}
		}catch (Exception e){
			log.error("支付微信模板消息异常", e);
			return null;
		}
		
	}
	
	
}
