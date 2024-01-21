package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

/**
 * 管理台查询返回
 * @author bianyatian
 * @2017-7-7 上午10:37:48
 */
public class BsSmsRecordJnlReVo{
    private Integer id;

    private String mobile;

    private String content;

    private Date sendTime;

    private String type;

    private String serialNo;

    private Date arriveTime;

    private Integer platformsId;
    
    private String platformsName;

    private Integer statusCode;

    private String statusMsg;
    
    private Date updateTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Integer getPlatformsId() {
        return platformsId;
    }

    public void setPlatformsId(Integer platformsId) {
        this.platformsId = platformsId;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

	public String getPlatformsName() {
		return platformsName;
	}

	public void setPlatformsName(String platformsName) {
		this.platformsName = platformsName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}