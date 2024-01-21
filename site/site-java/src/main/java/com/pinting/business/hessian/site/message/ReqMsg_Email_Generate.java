package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * @Project: site-java
 * @Title: ReqMsg_Email_Generate.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:40:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Email_Generate extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -887795972151480150L;
	private String email;//手机	 可选

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
