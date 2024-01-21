package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsErrorCode extends PageInfoObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = 787553668227760180L;

	private Integer id;

    private String channel;

    private String interfaceType;

    private String errorCode;

    private String errorInMsg;

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