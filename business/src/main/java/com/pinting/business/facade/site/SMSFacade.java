package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_SMS_Generate;
import com.pinting.business.hessian.site.message.ReqMsg_SMS_Interval;
import com.pinting.business.hessian.site.message.ReqMsg_SMS_SendMobiles;
import com.pinting.business.hessian.site.message.ReqMsg_SMS_Validation;
import com.pinting.business.hessian.site.message.ResMsg_SMS_Generate;
import com.pinting.business.hessian.site.message.ResMsg_SMS_Interval;
import com.pinting.business.hessian.site.message.ResMsg_SMS_SendMobiles;
import com.pinting.business.hessian.site.message.ResMsg_SMS_Validation;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
/**
 * @Project: business
 * @Title: SMSFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("SMS")
public class SMSFacade{
	@Autowired
	private SMSService smsService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	public void generate(ReqMsg_SMS_Generate req, ResMsg_SMS_Generate resp) {
		//具体发送手机验证码过程 -- 已经使用新的隔30秒发送一次
		resp.setMobileCode(smsService.sendIdentify(req.getMobile()));
		
	}
	public void validation(ReqMsg_SMS_Validation req, ResMsg_SMS_Validation resp) {
		//具体验证手机验证码过程
		resp.setIsValidateSuccess(smsService.validateIdentity(req.getMobile(), req.getMobileCode()));
	}
	
	public void sendMobiles(ReqMsg_SMS_SendMobiles req, ResMsg_SMS_SendMobiles resp) {
		String mobileType = req.getMobileType();
		if(Constants.MOBILE_TYPE_EMERGENCY.equals(mobileType)){
			BsSysConfig bsSysConfig = sysConfigService.findEmergencyMobile();
			req.setMobiles(bsSysConfig.getConfValue());
		}
		
		//String failedMobiles = smsService.sendMobiles(req.getMobiles(), req.getMessage());
		String failedMobiles = "";
		if(!smsServiceClient.sendTemplateMessage(req.getMobiles(), TemplateKey.COMMON_EMERGENCY, req.getMessage())){
			failedMobiles = req.getMobiles();
		}
		
		if(!"".equals(failedMobiles)){
			resp.setFailedMobiles(failedMobiles);
			throw new PTMessageException(PTMessageEnum.MOBILE_SEND_MESSAGE_ERROR);
		}
		
	}
	
	
	public void interval(ReqMsg_SMS_Interval req, ResMsg_SMS_Interval resp) {
		
		Integer interval = smsService.interval(req.getMobile());
		resp.setInterval(interval);

	}
	
}
