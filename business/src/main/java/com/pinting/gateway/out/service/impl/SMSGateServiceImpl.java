package com.pinting.gateway.out.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.pinting.business.util.SMSUtils;
import com.pinting.gateway.out.service.SMSGateService;
/**
 * @Project: business
 * @Title: SMSGateServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:13
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SMSGateServiceImpl implements SMSGateService{

	
	@Override
	@Deprecated
	public String sendIdentify(String mobile)  {
		return SMSUtils.sendToMobiles(mobile);
	}

	@Override
	@Deprecated
	public Boolean sendMessage(String mobile, String message) {
		if(StringUtils.isNotBlank(SMSUtils.sendSMS(mobile, message))){
			return true;
		}else{
			return false;
		}
		
	}

}
