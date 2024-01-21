package com.pinting.business.util;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: WxMessageUtil 
 * @Package com.mobweb.common.util.wechat
 * @Description: 推送微信消息工具类
 * @author sc
 * @return {
			"errcode":0, //请求成功
			"errmsg":"ok",
			"msgid":200228332
			}
 *
 */
public class WxMessageUtil {
	
	protected static final Logger logger = LoggerFactory.getLogger(WxMessageUtil.class);

	public static JSONObject sendWxMessage(WxTemplate template) {
		JSONObject obj = null;
		//TODO:因为微信appid 和secret 有后续需修改的，暂时注释掉这块没用上的
		String access_token = WeChatUtil.getAccessToken();
		String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
		String json = JSONObject.fromObject(template).toString();
		try {
			String result = WeChatUtil.sendPost(templateUrl, json, false, "UTF-8");
			obj = JSONObject.fromObject(result);
			logger.info("WxMessageUtil向用户推送微信消息，响应信息：{}", obj);
		}catch(Exception e) {
			logger.debug("WxMessageUtil向用户推送微信消息出现异常:--->e.getMessage()=" + e.getMessage());
		}
		return obj;
	}
	
	
	
	public static JSONObject sendWxMessage(String json) {
		JSONObject obj = null;
		
		String access_token = WeChatUtil.getAccessToken();
		String templateUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token;
		try {
			logger.info("====发送模板："+json);
			String result = WeChatUtil.sendPost(templateUrl, json, false, "UTF-8");
			obj = JSONObject.fromObject(result);
			logger.info("WxMessageUtil向用户推送微信消息，响应信息：{}", obj);
			String code = obj.get("errcode").toString();
        	if(Constants.WECHAT_SEND_SUCC_CODE.equals(code)){
        		return null;
        	}
		}catch(Exception e) {
			logger.debug("WxMessageUtil向用户推送微信消息出现异常:--->e.getMessage()=" + e.getMessage());
		}
		return obj;
	}
}
