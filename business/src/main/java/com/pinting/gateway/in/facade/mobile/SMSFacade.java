package com.pinting.gateway.in.facade.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_SMS_Generate;
import com.pinting.business.hessian.site.message.ResMsg_SMS_Generate;
import com.pinting.business.service.site.SMSService;
import com.pinting.gateway.in.util.InterfaceVersion;
/**
 * @Project: business
 * @Title: SMSFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("appSMS")
public class SMSFacade{
	@Autowired
	private SMSService smsService;
	
	@InterfaceVersion("Generate/1.0.0")
	public void generate(ReqMsg_SMS_Generate req, ResMsg_SMS_Generate resp) {
		//具体发送手机验证码过程 -- 已经使用新的隔30秒发送一次
		resp.setMobileCode(smsService.sendIdentify(req.getMobile()));
		
	}
	
}
