package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.model.BsWxUserInfo;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.service.site.WxUserService;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.WxMessageUtil;
import com.pinting.business.util.WxTemplate;
import com.pinting.business.util.WxTemplateData;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

@Service
public class SendWechatServiceImpl implements SendWechatService {
	private static Logger log = LoggerFactory.getLogger(SendWechatServiceImpl.class);
	private static String urlHeader = GlobEnvUtil.get("wechat.server.web");
	String payment = GlobEnvUtil.get("wechat.template.payment");
	String buySucOrFall = GlobEnvUtil.get("wechat.template.buySucOrFail");
	String withdraw = GlobEnvUtil.get("wechat.template.withdrawSucOrFail");
	String topUp = GlobEnvUtil.get("wechat.template.topUpSucOrFail");
	String redPacket = GlobEnvUtil.get("wechat.template.redPacket");
	String chargeAuthActBack = GlobEnvUtil.get("wechat.template.chargeAuthActBack");
	String bonusArrive = GlobEnvUtil.get("wechat.template.bonusArrive");
	// String ticketWechatTemlate = GlobEnvUtil.get("wechat.template.ticket");
	//	String ticketReminderWechatTemlate = GlobEnvUtil.get("wechat.template.ticketReminder");

	@Autowired
	private WxUserService wxUserService;
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
	
	@Override
	public String paymentMgs2Card(Integer userId, String userNick,
			String paymentAmount, String principal, String proceeds, String cardNo) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--回款成功通知
				template.setTemplate_id(payment);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的" + userNick
						+ "用户，您有一笔投资已回款至尾号"+cardNo+"的银行卡。", "#173177"));
				data.put("keyword1", new WxTemplateData(MoneyUtil.format(paymentAmount)+"元", "#173177"));
				data.put("keyword2", new WxTemplateData(MoneyUtil.format(principal)+"元", "#173177"));
				data.put("keyword3",new WxTemplateData(MoneyUtil.format(proceeds)+"元","#173177"));
				data.put("remark", new WxTemplateData(
						"币港湾欢迎您再次投资。如有疑问，请致电客服电话400-806-1230。", "#173177"));
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
		} catch (Exception e) {
			log.error("支付微信模板消息异常", e);
			return null;
		}
	}
	
	

	@Override
	public String paymentMgs2Balance(Integer userId, String userNick,
			String paymentAmount, String principal, String proceeds) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--回款成功通知
				template.setTemplate_id(payment);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的" + userNick
						+ "用户，您有一项计划已回款至您的币港湾账户余额。", "#173177"));
				data.put("keyword1", new WxTemplateData(MoneyUtil.format(paymentAmount)+"元", "#173177"));
				data.put("keyword2", new WxTemplateData(MoneyUtil.format(principal)+"元", "#173177"));
				data.put("keyword3",new WxTemplateData(MoneyUtil.format(proceeds)+"元","#173177"));
				data.put("remark", new WxTemplateData(
						"币港湾欢迎您再次加入。如有疑问，请致电客服电话400-806-1230。", "#173177"));
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
		} catch (Exception e) {
			log.error("支付微信模板消息异常", e);
			return null;
		}
	}


	@Override
	public String buyProductMgs(Integer userId, String userNick, String productName, String income,
			String corpus, String days, String sucOrFall, String fallReason) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--投资通知
				template.setTemplate_id(buySucOrFall);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				if("suc".equals(sucOrFall)){
					data.put("first", new WxTemplateData("尊敬的用户，您有一项计划已加入，次日起息。", "#173177"));
					data.put("remark", new WxTemplateData(
							"币港湾欢迎您再次加入产品项目。如有疑问，请致电客服电话400-806-1230。", "#173177"));
				}else{
					String s = "尊敬的用户，很抱歉您有一项计划未成功";
					if(StringUtil.isNotBlank(fallReason)){
						s = s + "，失败原因："+fallReason;
					}
					data.put("first", new WxTemplateData(s+"。", "#173177"));
					data.put("remark", new WxTemplateData(
							"备注：如有疑问，请致电客服电话400-806-1230。", "#173177"));
				}
				
				data.put("keyword1", new WxTemplateData(productName, "#173177"));
				data.put("keyword2", new WxTemplateData(income, "#173177"));
				data.put("keyword3",new WxTemplateData(MoneyUtil.format(corpus)+"元","#173177"));
				data.put("keyword4",new WxTemplateData(days+"天","#173177"));
				template.setData(data);
				JSONObject obj = WxMessageUtil.sendWxMessage(template);
				if(obj != null){
					log.error("理财购买微信模板消息返回结果：{}", obj.toString());
					return obj.get("errcode").toString();	
				}else{
					return null;
				}
				
			}else{
				return "该用户无openId";
			}
		} catch (Exception e) {
			log.error("理财购买微信模板消息异常", e);
			return null;
		}
		
	}

	@Override
	public String withdrawMgs(Integer userId, String userNick, String cardNo,
			String money, String sucOrFall, String fallReason, String fee, String actInAmount) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--提现结果通知
				template.setTemplate_id(withdraw);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				String note = "备注：";
				if("suc".equals(sucOrFall)){
					data.put("first", new WxTemplateData("尊敬的" + userNick
							+ "用户，您有一笔提现已成功。", "#173177"));
					data.put("keyword4",new WxTemplateData("成功","#173177"));
					note = note + "手续费："+fee+"元，实际到账："+actInAmount+"元。实际到账时间以入款行为准，";
				}else{
					String s = "尊敬的" + userNick+ "用户，很抱歉您有一笔账户提现未成功";
					if(StringUtil.isNotBlank(fallReason)){
						s = s + "，失败原因："+fallReason;
					}
					data.put("first", new WxTemplateData(s+"。", "#173177"));
					data.put("keyword4",new WxTemplateData("失败","#173177"));
				}
				
				note = note + "如有疑问，请致电客服电话400-806-1230。";
				data.put("keyword1", new WxTemplateData("卡号后4位"+cardNo, "#173177"));
				data.put("keyword2", new WxTemplateData(MoneyUtil.format(money)+"元", "#173177"));
				data.put("keyword3",new WxTemplateData(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"),"#173177"));
				
				data.put("remark", new WxTemplateData(
						note, "#173177"));
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
		} catch (Exception e) {
			log.error("提现微信模板消息异常", e);
			return null;
		}
		
	}

	@Override
	public String topUpMgs(Integer userId, String userNick, String mobile,
			String money, String sucOrFall, String fallReason) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--充值通知
				template.setTemplate_id(topUp);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				if("suc".equals(sucOrFall)){
					data.put("first", new WxTemplateData("尊敬的" + userNick
							+ "用户，您已经成功充值。", "#173177"));
					data.put("result",new WxTemplateData("成功","#173177"));
				}else{
					String s = "尊敬的" + userNick+ "用户，抱歉的通知您，您的充值失败了";
					if(StringUtil.isNotBlank(fallReason)){
						s = s + "，失败原因："+fallReason;
					}
					data.put("first", new WxTemplateData(s+"。", "#173177"));
					data.put("result",new WxTemplateData("失败","#173177"));
				}
				
				data.put("accountType", new WxTemplateData("账户ID", "#000000"));
				data.put("account", new WxTemplateData(StringUtil.left(mobile, 3)+"****"+StringUtil.right(mobile, 4), "#173177"));
				data.put("amount",new WxTemplateData(MoneyUtil.format(money)+"元","#173177"));
				data.put("remark", new WxTemplateData(
						"备注：如有疑问，请致电客服电话400-806-1230。", "#173177"));
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
		} catch (Exception e) {
			log.error("充值微信模板消息异常", e);
			return null;
		}
		
	}

	@Override
	public String packetSend(Integer userId, String packetAmount,
			String packetName) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--领取红包通知
				template.setTemplate_id(redPacket);
				template.setUrl(urlHeader + "/micro2.0/redPacket/myRedPacket");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("国资背景，安心收益，币港湾给您送红包啦！", "#173177"));
				data.put("keyword1", new WxTemplateData(packetName, "#173177"));
				data.put("keyword2", new WxTemplateData("合计" + packetAmount + "元抵扣红包", "#173177"));
				data.put("remark", new WxTemplateData(
						"备注：红包直接抵扣现金，记得使用哦！", "#173177"));
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
		} catch (Exception e) {
			log.error("红包发送微信模板消息异常", e);
			return null;
		}
	}

	@Override
	public String chargeAuthActBackSend(Integer userId, String openBalance,
			String balance,String productId,String subAccountId,String openTime) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				template.setTemplate_id(chargeAuthActBack);
				template.setUrl(urlHeader + "/micro2.0/match/myMatch?productId="+productId+"&subAccountId="+subAccountId);
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的用户，您有一笔委托出借已完成。", "#173177"));
				//data.put("keyword1", new WxTemplateData(openBalance+"元", "#173177"));
				data.put("keyword1",new WxTemplateData(openTime,"#173177"));
				data.put("keyword2", new WxTemplateData(balance+"元", "#173177"));
				data.put("remark", new WxTemplateData(
						"未出借资金已转入您的可用余额，您可以再次委托出借或提现。", "#173177"));
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
		} catch (Exception e) {
			log.error("站岗户资金自动退回微信模板消息异常", e);
			return null;
		}
	}

	@Override
	public String buyProductMgs4Zan(Integer userId, String userNick,
			String productName, String income, String corpus, String months,
			String sucOrFall, String fallReason) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--投资通知
				template.setTemplate_id(buySucOrFall);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				if("suc".equals(sucOrFall)){
					data.put("first", new WxTemplateData("尊敬的用户，您有一项计划已加入，次日起息。", "#173177"));
					data.put("remark", new WxTemplateData(
							"币港湾欢迎您再次加入产品项目。如有疑问，请致电客服电话400-806-1230。", "#173177"));
				}else{
					String s = "尊敬的用户，很抱歉您有一项计划未成功";
					if(StringUtil.isNotBlank(fallReason)){
						s = s + "，失败原因："+fallReason;
					}
					data.put("first", new WxTemplateData(s+"。", "#173177"));
					data.put("remark", new WxTemplateData(
							"备注：如有疑问，请致电客服电话400-806-1230。", "#173177"));
				}

				data.put("keyword1", new WxTemplateData(productName, "#173177"));
				data.put("keyword2", new WxTemplateData(income, "#173177"));
				data.put("keyword3",new WxTemplateData(MoneyUtil.format(corpus)+"元","#173177"));
				data.put("keyword4",new WxTemplateData(months+"个月","#173177"));
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
		} catch (Exception e) {
			log.error("理财购买微信模板消息异常", e);
			return null;
		}
	}

	@Override
	public String finishLoanSend(Integer userId, String openBalance,
			String productId, String subAccountId, String openTime,String borrowAmount) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				log.info("===========最后一笔出借完成发送微信模板消息===========");
				template.setTouser(getOpenId(userId));
				template.setTemplate_id(chargeAuthActBack);
				template.setUrl(urlHeader + "/micro2.0/match/myMatch?productId="+productId+"&subAccountId="+subAccountId);
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
//				data.put("first", new WxTemplateData("尊敬的用户，您有一笔金额为"+openBalance+"元的分期类出借委托已完成。", "#173177"));
				data.put("first", new WxTemplateData("尊敬的用户，您有一笔委托出借已完成。", "#173177"));
				//data.put("keyword1", new WxTemplateData(openBalance+"元", "#173177"));
				data.put("keyword1",new WxTemplateData(openTime,"#173177"));
				data.put("keyword2", new WxTemplateData(openBalance+"元", "#173177"));
//				data.put("keyword2", new WxTemplateData(borrowAmount+"元", "#173177"));
//				double remainAmount=MoneyUtil.subtract(openBalance,borrowAmount).doubleValue();
//				data.put("remark", new WxTemplateData("已回款"+remainAmount+"元，回款资金已转入您的可用余额中，您可再次委托出借或提现。", "#173177"));
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
		} catch (Exception e) {
			log.error("最后一笔出借完成发送微信模板消息异常", e);
			return null;
		}
	}

	@Override
	public String bonusWithdrawMgs(Integer userId, String userNick,
			String cardNo, String money, String sucOrFall, String fallReason) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--提现结果通知
				template.setTemplate_id(withdraw);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				String note = "备注：";
				if("suc".equals(sucOrFall)){
					data.put("first", new WxTemplateData("尊敬的" + userNick
							+ "客户，您有一笔奖励金提现已成功。", "#173177"));
					data.put("keyword4",new WxTemplateData("成功","#173177"));
					
				}else{
					String s = "尊敬的" + userNick+ "客户，您有一笔奖励金提现交易失败。";
					if(StringUtil.isNotBlank(fallReason)){
						note = note + "失败原因："+fallReason+"。";
					}else{
						note = note + "失败原因：未知。";
					}
					data.put("first", new WxTemplateData(s, "#173177"));
					data.put("keyword4",new WxTemplateData("失败","#173177"));
				}
				note = note + "如有疑问请拨打400-806-1230。";
				
				data.put("keyword1", new WxTemplateData("卡号后4位"+cardNo, "#173177"));
				data.put("keyword2", new WxTemplateData(MoneyUtil.format(money)+"元", "#173177"));
				data.put("keyword3",new WxTemplateData(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"),"#173177"));
				
				data.put("remark", new WxTemplateData(
						note, "#173177"));
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
		} catch (Exception e) {
			log.error("提现微信模板消息异常", e);
			return null;
		}
	}

	@Override
	public String paymentMgs2Balance4Zan(Integer userId, String paymentAmount,
			String principal, String proceeds, String endTime) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--回款成功通知
				template.setTemplate_id(payment);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的用户，您有一项计划已回款至您的币港湾账户余额。", "#173177"));
				data.put("keyword1", new WxTemplateData(MoneyUtil.format(paymentAmount)+"元", "#173177"));
				data.put("keyword2", new WxTemplateData(MoneyUtil.format(principal)+"元", "#173177"));
				data.put("keyword3",new WxTemplateData(MoneyUtil.format(proceeds)+"元","#173177"));
				data.put("remark", new WxTemplateData(
						"回款时间："+endTime+"币港湾欢迎您再次加入。如有疑问，请致电客服电话400-806-1230。", "#173177"));
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
		} catch (Exception e) {
			log.error("支付微信模板消息异常", e);
			return null;
		}
	}

	@Override
	public String bonusArrive(Integer userId, String amount, String finishTime) {
		try {
			WxTemplate template = new WxTemplate();
			if(StringUtil.isNotBlank(getOpenId(userId))){
				template.setTouser(getOpenId(userId));
				// 标题--回款成功通知
				template.setTemplate_id(bonusArrive);
				template.setUrl(urlHeader + "/micro2.0/assets/assets");
				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
				data.put("first", new WxTemplateData("尊敬的客户，您有一笔奖励金已到账。", "#173177"));
				data.put("income_amount", new WxTemplateData(MoneyUtil.format(amount)+"元", "#173177"));
				data.put("income_time", new WxTemplateData(finishTime, "#173177"));
				data.put("remark", new WxTemplateData("备注：如有疑问请拨打400-806-1230。", "#173177"));
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
		} catch (Exception e) {
			log.error("奖励金到账通知模板消息异常", e);
			return null;
		}
	}

	@Override
	public String ticketInterestGrant(Integer userId, String name, Double rate) {
//		try {
//			WxTemplate template = new WxTemplate();
//			if(StringUtil.isNotBlank(getOpenId(userId))){
//				template.setTouser(getOpenId(userId));
//				// 标题--回款成功通知
//				template.setTemplate_id(ticketWechatTemlate);
//				template.setUrl(urlHeader + "/micro2.0/assets/assets");
//				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
//				data.put("first", new WxTemplateData("国资背景，安心收益，恭喜你获得加息券。", "#173177"));
//				data.put("keyword1", new WxTemplateData(name, "#173177"));
//				data.put("keyword2", new WxTemplateData(MoneyUtil.format(rate) + "%" + name, "#173177"));
//				data.put("remark", new WxTemplateData(
//						"备注：如有疑问，请致电客服电话400-806-1230。", "#173177"));
//				template.setData(data);
//				JSONObject obj = WxMessageUtil.sendWxMessage(template);
//				if(obj != null){
//					log.error(obj.toString());
//					return obj.get("errcode").toString();
//				}else{
//					return null;
//				}
//			}else{
//				return "该用户无openId";
//			}
//		} catch (Exception e) {
//			log.error("支付微信模板消息异常", e);
//			return null;
//		}
		return null;
	}

	@Override
	public String ticketReminderNotify(Integer userId, Integer ticketCount, String dueDate) {
//		try {
//			WxTemplate template = new WxTemplate();
//			if(StringUtil.isNotBlank(getOpenId(userId))){
//				template.setTouser(getOpenId(userId));
//				// 标题--券过期提醒
//				template.setTemplate_id(ticketReminderWechatTemlate);
//				template.setUrl(urlHeader + "/micro2.0/assets/assets");
//				Map<String, WxTemplateData> data = new HashMap<String, WxTemplateData>();
//				data.put("first", new WxTemplateData("尊敬的客户", "#173177"));
//				data.put("keyword1", new WxTemplateData(ticketCount+"张", "#173177"));
//				data.put("keyword2", new WxTemplateData(dueDate, "#173177"));
//				data.put("remark", new WxTemplateData("尊敬的用户，您有" + ticketCount + "个优惠券将于3天后过期（" + dueDate
//						+ "为最后可用期限），请尽快使用。", "#173177"));
//				template.setData(data);
//				JSONObject obj = WxMessageUtil.sendWxMessage(template);
//				if(obj != null){
//					log.error(obj.toString());
//					return obj.get("errcode").toString();
//				}else{
//					return null;
//				}
//			}else{
//				return "该用户无openId";
//			}
//		} catch (Exception e) {
//			log.error("券过期提醒模板消息异常", e);
//			return null;
//		}
		return null;
	}

}
