package com.pinting.business.hessian.site.message;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_Feedback extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3575515420261286534L;
	@NotNull(message="用户编号不能为空")
	private Integer userId;
	@NotNull(message="意见反馈不能为空")
	@Pattern(message="意见反馈不能超过500字符", regexp="^[\\w\\W]{1,500}$")
	private String info;
	private Date createTime;
    private Date replyTime;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}
}
