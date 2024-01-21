package com.pinting.business.hessian.site.message;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: business
 * @Title: ReqMsg_Profile_RealNameBind.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:45:56
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_RealNameBind extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3195193706105666183L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	@NotNull(message="姓名不能为空")
	@Pattern(regexp="^[\\u4e00-\\u9fa5]{1,20}$", message="姓名格式错误")
	private String userName;
	@NotNull(message="身份证号码不能为空")
	@Pattern(regexp="(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d{1}|X|x)$)", message="身份证号码格式错误")
	private String idCard;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	
	
	
}
