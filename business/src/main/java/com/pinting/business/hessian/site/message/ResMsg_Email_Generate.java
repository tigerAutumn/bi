package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * @Project: business
 * @Title: ResMsg_Email_Generate.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:46:37
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_Email_Generate extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5720633731994886012L;
	private String emailCode;//	生成的验证码	选填		
	
	public String getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}
}
