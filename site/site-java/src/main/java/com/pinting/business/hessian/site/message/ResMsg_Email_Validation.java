package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * @Project: site-java
 * @Title: ResMsg_Email_Validation.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:42:17
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_Email_Validation extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 124779005423387082L;
	private Boolean isValidateSuccess;//	是否验证成功	选填	

	public Boolean getIsValidateSuccess() {
		return isValidateSuccess;
	}

	public void setIsValidateSuccess(Boolean isValidateSuccess) {
		this.isValidateSuccess = isValidateSuccess;
	}

	
}
