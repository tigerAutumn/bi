package com.pinting.business.facade.site;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import com.pinting.business.hessian.site.message.ReqMsg_SWxUtil_GetTokenAndTicket;
import com.pinting.business.hessian.site.message.ResMsg_SWxUtil_GetTokenAndTicket;
import com.pinting.business.util.WeChatUtil;

@Component("SWxUtil")
/**
 * 微信工具类
 * @Project: site
 * @Title: WxUtilFacade.java
 * @author yanwl
 * @date 2015-12-09 上午10:39:39
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class SWxUtilFacade {
	private Logger log = LoggerFactory.getLogger(SWxUtilFacade.class);
	
	public void getTokenAndTicket(ReqMsg_SWxUtil_GetTokenAndTicket req, ResMsg_SWxUtil_GetTokenAndTicket res) {
		String accessToken = WeChatUtil.getAccessToken();
		log.info("s-accessToken:"+accessToken);

		String ticket = WeChatUtil.getJsapiTicket();
		log.info("s-ticket:"+ticket);
		res.setAccessToken(accessToken);
		res.setJsapiTicket(ticket);
		res.setAppid(WeChatUtil.appid);
		res.setSecret(WeChatUtil.secret);
		res.setToken(WeChatUtil.token);
	}
}
