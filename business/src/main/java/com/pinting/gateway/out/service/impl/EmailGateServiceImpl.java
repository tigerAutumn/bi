package com.pinting.gateway.out.service.impl;


import org.springframework.stereotype.Service;

import com.pinting.business.util.MailUtil;
import com.pinting.gateway.out.service.EmailGateService;
/**
 * @Project: business
 * @Title: EmailGateServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:06
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class EmailGateServiceImpl implements EmailGateService{

	@Override
	public String sendIdentify(String email) {
		return MailUtil.sendToEmail(email);
	}

}
