package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Profile_UrgentBind.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:46:00
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_UrgentBind extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1045304923105406555L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	@NotNull(message="紧急联系人姓名不能为空")
	@Pattern(regexp="^[\\u4e00-\\u9fa5]{1,20}$", message="紧急联系人姓名格式错误")
	private String urgentName;
	@NotNull(message="紧急联系人手机不能为空")
	@Pattern(regexp="^[1][34587]\\d{9}$", message="手机格式错误")
	private String urgentMobile;
	@NotNull(message="紧急联系人关系不能为空")
	private Integer relation;
	@NotNull(message="支付密码不能为空")
	@Pattern(regexp="^[0-9a-zA-Z_]{6,16}$",message="支付密码格式错误")
	private String payPassWord;
	
	public String getPayPassWord() {
		return payPassWord;
	}
	public void setPayPassWord(String payPassWord) {
		this.payPassWord = payPassWord;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUrgentName() {
		return urgentName;
	}
	public void setUrgentName(String urgentName) {
		this.urgentName = urgentName;
	}
	public String getUrgentMobile() {
		return urgentMobile;
	}
	public void setUrgentMobile(String urgentMobile) {
		this.urgentMobile = urgentMobile;
	}
	public Integer getRelation() {
		return relation;
	}
	public void setRelation(Integer relation) {
		this.relation = relation;
	}
	
	
}
