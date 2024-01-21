package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_SMS_Generate.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:43:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_SMS_Generate extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -887795972151480150L;
	private String mobile;//手机	 可选

	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
