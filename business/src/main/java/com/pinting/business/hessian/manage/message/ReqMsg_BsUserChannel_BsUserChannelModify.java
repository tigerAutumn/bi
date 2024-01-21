package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUserChannel_BsUserChannelModify extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8491777168035334298L;
	
	private Integer id;

    private Integer userId;

    private Integer bankChannelId;

    private Date createTime;
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getBankChannelId() {
		return bankChannelId;
	}

	public void setBankChannelId(Integer bankChannelId) {
		this.bankChannelId = bankChannelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
