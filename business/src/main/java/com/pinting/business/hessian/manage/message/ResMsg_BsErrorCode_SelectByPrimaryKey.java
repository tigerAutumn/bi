package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsErrorCode_SelectByPrimaryKey extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5403684324812187757L;
	
	private Integer id;
	
	/** 渠道类型 */
	private String channel;

    /** 接口类型 */
	private String interfaceType;

	/** 错误码 */
	private String errorCode;

	/** 错误码内部描述 */
	private String errorInMsg;

	/** 错误码外部描述 */
	private String errorOutMsg;
	
	private Date createTime;

    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInMsg() {
		return errorInMsg;
	}

	public void setErrorInMsg(String errorInMsg) {
		this.errorInMsg = errorInMsg;
	}

	public String getErrorOutMsg() {
		return errorOutMsg;
	}

	public void setErrorOutMsg(String errorOutMsg) {
		this.errorOutMsg = errorOutMsg;
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
