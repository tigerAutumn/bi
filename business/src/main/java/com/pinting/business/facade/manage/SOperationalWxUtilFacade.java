package com.pinting.business.facade.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_SOperationalWxUtil_GetTokenAndTicket;
import com.pinting.business.hessian.manage.message.ResMsg_SOperationalWxUtil_GetTokenAndTicket;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.OperationalWeChatUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

@Component("SOperationalWxUtil")
/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午3:49:33
 */
public class SOperationalWxUtilFacade {
	
	private Logger log = LoggerFactory.getLogger(SOperationalWxUtilFacade.class);
	  
	public void getTokenAndTicket(ReqMsg_SOperationalWxUtil_GetTokenAndTicket req, ResMsg_SOperationalWxUtil_GetTokenAndTicket res) {
		
		String appId = GlobEnvUtil.get("wechat.operational.appid");
		String secret = GlobEnvUtil.get("wechat.operational.secret");
		String token = GlobEnvUtil.get("wechat.operational.token");
		String dsUrl = GlobEnvUtil.get("dataSource.url");
		log.info("appId:{}，secret:{}，token:{}，dsUrl:{}", appId, secret, token, dsUrl);
		String wxAppId = StringUtil.isEmpty(appId) ? "" : appId;
		String wxSecret = StringUtil.isEmpty(secret) ? "" : secret;
		String wxToken = StringUtil.isEmpty(token) ? "" : token;
		String accessToken = OperationalWeChatUtil.getAccessToken(req.getRequestFlag(), wxAppId, wxSecret);
		log.info("s-accessToken:"+accessToken);
		String ticket = OperationalWeChatUtil.getJsapiTicket(req.getRequestFlag(), wxAppId, wxSecret);
		log.info("s-ticket:"+ticket);
		res.setAccessToken(accessToken);
		res.setJsapiTicket(ticket);
		res.setAppid(wxAppId);
		res.setSecret(wxSecret);
		res.setToken(wxToken);
		log.info("s-appid:"+res.getAppid());
		log.info("s-secret:"+res.getSecret());
		log.info("s-token:"+res.getToken());
	}
	
}
