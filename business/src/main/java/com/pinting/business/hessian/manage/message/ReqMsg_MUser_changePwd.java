package com.pinting.business.hessian.manage.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MUser_changePwd extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9187017215205801399L;

	private Integer id;//	用户编号	必填		
	@NotNull(message="密码不能为空")
	@Pattern(regexp="^[a-zA-Z0-9_!@#$]{8,16}$",message="密码格式错误")
	private String password;
	@NotNull(message="密码不能为空")
	@Pattern(regexp="^[a-zA-Z0-9_!@#$]{8,16}$",message="密码格式错误")
	private String oldPassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}
