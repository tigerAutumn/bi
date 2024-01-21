package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Profile_UrgentBind.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:45
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_UrgentBind extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1045304923105406555L;
	
	private Integer userId;
	private String urgentName;
	private String urgentMobile;
	private Integer relation;
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
