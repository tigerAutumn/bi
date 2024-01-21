package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Fund_CheckIn extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3615869961469798277L;
	@NotNull(message="姓名不能为空")
	@Pattern(regexp="^[\\u4e00-\\u9fa5]{1,20}$", message="姓名格式错误")
	private String name;
	@NotNull(message="手机不能为空")
	@Pattern(regexp="^[1][34587]\\d{9}$", message="手机格式错误")
	private String mobile;
	
	private String city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}
