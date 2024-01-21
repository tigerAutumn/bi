package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Email_Generate;
import com.pinting.business.hessian.site.message.ReqMsg_Email_Validation;
import com.pinting.business.hessian.site.message.ResMsg_Email_Generate;
import com.pinting.business.hessian.site.message.ResMsg_Email_Validation;
import com.pinting.business.service.site.EmailService;
import com.pinting.core.exception.PTException;
/**
 * @Project: business
 * @Title: EmailFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:11
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Email")
public class EmailFacade{
	@Autowired
	private EmailService emailService;
	
	public void generate(ReqMsg_Email_Generate req, ResMsg_Email_Generate resp) {
		//具体发送邮箱验证码过程
		resp.setEmailCode(emailService.sendIdentify(req.getEmail()));
	}
	public void validation(ReqMsg_Email_Validation req, ResMsg_Email_Validation resp) {
		//具体验证邮箱验证码过程
		resp.setIsValidateSuccess(emailService.validateIdentity(req.getEmail(), req.getEmailCode()));
	}
}
