package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;


public class ReqMsg_BsUser_BsUserSaveRecord extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2653616838727025763L;

	private Integer userId;
	private Date submitTime;
	private String submitter;
	private String content;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date date) {
		this.submitTime = date;
	}

	public String getSubmitter() {
		return submitter;
	}

	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
