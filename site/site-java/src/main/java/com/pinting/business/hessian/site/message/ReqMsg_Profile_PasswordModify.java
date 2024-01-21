package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Profile_PasswordModify.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:32
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_PasswordModify extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7355272564785630363L;
	
	private Integer userId;
	private String oldPassWord;
	private String newPassWord;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOldPassWord() {
		return oldPassWord;
	}
	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}
	public String getNewPassWord() {
		return newPassWord;
	}
	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	
	
	
	
}
