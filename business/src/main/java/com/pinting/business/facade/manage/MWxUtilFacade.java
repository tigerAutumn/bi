package com.pinting.business.facade.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import com.pinting.business.hessian.manage.message.ReqMsg_MWxUtil_GetTokenAndTicket;
import com.pinting.business.hessian.manage.message.ResMsg_MWxUtil_GetTokenAndTicket;
import com.pinting.business.util.WeChatUtil;

@Component("MWxUtil")
/**
 * 微信工具类
 * @Project: manage
 * @Title: WxUtilFacade.java
 * @author yanwl
 * @date 2015-12-09 上午10:39:39
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class MWxUtilFacade {
	private Logger log = LoggerFactory.getLogger(MWxUtilFacade.class);
	
	public void getTokenAndTicket(ReqMsg_MWxUtil_GetTokenAndTicket req, ResMsg_MWxUtil_GetTokenAndTicket res) {
		String accessToken = WeChatUtil.getAccessToken();
		log.info("m-accessToken:"+accessToken);
		String ticket = WeChatUtil.getJsapiTicket();
		log.info("m-ticket:"+ticket);
		res.setAccessToken(accessToken);
		res.setJsapiTicket(ticket);
	}
}
