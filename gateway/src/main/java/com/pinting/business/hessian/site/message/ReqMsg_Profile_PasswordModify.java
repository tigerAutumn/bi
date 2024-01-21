package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Profile_PasswordModify.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_PasswordModify extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3803373459129672630L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	@NotNull(message="旧密码不能为空")
	@Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="旧密码格式错误")
	private String oldPassWord;
	@NotNull(message="新密码不能为空")
	@Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="新密码格式错误")
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
