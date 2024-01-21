package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Profile_EmailBind.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:06
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_EmailBind extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 815238311368869762L;
	private Integer userID;//
	private Boolean flag;
	private String email;//
	private String emailCode;
	private String newEmail;
	private String newEmailCode;
	private String oldEmail;
	private String oldEmailCode;
	
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Integer getUserID() {
		return userID;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailCode() {
		return emailCode;
	}

	public void setEmailCode(String emailCode) {
		this.emailCode = emailCode;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getOldEmail() {
		return oldEmail;
	}
	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
	public String getNewEmail() {
		return newEmail;
	}
	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}
	public String getOldEmailCode() {
		return oldEmailCode;
	}
	public void setOldEmailCode(String oldEmailCode) {
		this.oldEmailCode = oldEmailCode;
	}
	public String getNewEmailCode() {
		return newEmailCode;
	}
	public void setNewEmailCode(String newEmailCode) {
		this.newEmailCode = newEmailCode;
	}
	
}
