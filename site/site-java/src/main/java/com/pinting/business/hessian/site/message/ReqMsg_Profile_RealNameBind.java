package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Profile_RealNameBind.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_RealNameBind extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3195193706105666183L;
	
	private Integer userId;
	private String userName;
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
