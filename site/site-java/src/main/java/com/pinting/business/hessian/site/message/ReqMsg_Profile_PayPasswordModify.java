package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * @Project: site-java
 * @Title: ReqMsg_Profile_PayPasswordModify.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:41:36
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_Profile_PayPasswordModify extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2121198483355943972L;
	
	private Integer userId;
	private String oldPayPassWord;
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
