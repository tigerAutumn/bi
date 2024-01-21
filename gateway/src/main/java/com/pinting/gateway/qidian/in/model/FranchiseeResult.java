package com.pinting.gateway.qidian.in.model;

import java.util.Date;

/**
 * 
 * @project gateway
 * @title FranchiseeResult.java
 * @author Dragon & cat
 * @date 2018-3-20
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class FranchiseeResult {
	/**七店店主id*/
	private 	String 		franchiseeId;
	/**邀请链接*/
	private 	String 		inviteLink;
	/**币港湾店主编号*/
	private 	String 		bgwFranchiseeId;
	/**更新时间*/
	private 	Date 		updateTime;
	public String getFranchiseeId() {
		return franchiseeId;
	}
	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}
	public String getInviteLink() {
		return inviteLink;
	}
	public void setInviteLink(String inviteLink) {
		this.inviteLink = inviteLink;
	}
	public String getBgwFranchiseeId() {
		return bgwFranchiseeId;
	}
	public void setBgwFranchiseeId(String bgwFranchiseeId) {
		this.bgwFranchiseeId = bgwFranchiseeId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
