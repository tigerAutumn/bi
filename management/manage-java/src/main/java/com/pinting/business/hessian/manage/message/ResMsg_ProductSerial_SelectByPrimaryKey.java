package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_ProductSerial_SelectByPrimaryKey extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3671669920021248082L;
	
	private Integer id;

    private String serialName;

    private Integer term;

    private Date createTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
