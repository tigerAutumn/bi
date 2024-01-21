package com.pinting.business.model;

import java.util.Date;

public class BsSmsPlatformsConfig {
	
    private Integer id;

	private String platformsName;

	private String platformsCode;

	private String platformsType;

	private Integer priority;

	private String platformsAct;

	private String platformsPass;

	private String note;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlatformsName() {
		return platformsName;
	}

	public void setPlatformsName(String platformsName) {
		this.platformsName = platformsName;
	}

	public String getPlatformsCode() {
		return platformsCode;
	}

	public void setPlatformsCode(String platformsCode) {
		this.platformsCode = platformsCode;
	}

	public String getPlatformsType() {
		return platformsType;
	}

	public void setPlatformsType(String platformsType) {
		this.platformsType = platformsType;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public String getPlatformsAct() {
		return platformsAct;
	}

	public void setPlatformsAct(String platformsAct) {
		this.platformsAct = platformsAct;
	}

	public String getPlatformsPass() {
		return platformsPass;
	}

	public void setPlatformsPass(String platformsPass) {
		this.platformsPass = platformsPass;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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