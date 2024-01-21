package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * @Project: business
 * @Title: ResMsg_SMS_Generate.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:47:11
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_SMS_Generate extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5720633731994886012L;
	private String mobileCode;//	生成的验证码	选填		
	private Integer residueDegree;//当天剩余次数
	public String getMobileCode() {
		return mobileCode;
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public Integer getResidueDegree() {
		return residueDegree;
	}

	public void setResidueDegree(Integer residueDegree) {
		this.residueDegree = residueDegree;
	}

	
}
