package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_SaveQuestionnaire extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4383071964652437079L;

	/** 用户id */
	private Integer userId;
	/** 分数 */
	private Integer score;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	
}
