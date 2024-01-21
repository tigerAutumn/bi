package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * @Project: site-java
 * @Title: ResMsg_User_FindPayPassword.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:43:26
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ResMsg_User_FindPayPassword extends ResMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4355164923995523106L;
	private Boolean isValidateSuccess;//	是否验证成功	选填	

	public Boolean getIsValidateSuccess() {
		return isValidateSuccess==null?false : isValidateSuccess;
	}

	public void setIsValidateSuccess(Boolean isValidateSuccess) {
		this.isValidateSuccess = isValidateSuccess;
	}
	
}
