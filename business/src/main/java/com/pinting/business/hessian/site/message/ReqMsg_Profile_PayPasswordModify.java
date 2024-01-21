package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Profile_PayPasswordModify.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:53
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_PayPasswordModify extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2121198483355943972L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	@NotNull(message="旧支付密码不能为空")
	@Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="旧支付密码格式错误")
	private String oldPayPassWord;
	@NotNull(message="新支付密码不能为空")
	@Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="新支付密码格式错误")
	private String newPayPassWord;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOldPayPassWord() {
		return oldPayPassWord;
	}
	public void setOldPayPassWord(String oldPayPassWord) {
		this.oldPayPassWord = oldPayPassWord;
	}
	public String getNewPayPassWord() {
		return newPayPassWord;
	}
	public void setNewPayPassWord(String newPayPassWord) {
		this.newPayPassWord = newPayPassWord;
	}
	
}
