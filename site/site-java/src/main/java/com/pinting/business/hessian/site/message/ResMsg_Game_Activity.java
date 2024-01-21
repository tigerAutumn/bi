package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Game_Activity extends ResMsg {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -705124538458838511L;

	private Integer id;

	private String name;
	private Date start_time;
	private Date end_time;
	private Integer activityUserId;
	private boolean played;
	private Integer helperWxUserId;
	
	public Integer getHelperWxUserId() {
		return helperWxUserId;
	}
	public void setHelperWxUserId(Integer helperWxUserId) {
		this.helperWxUserId = helperWxUserId;
	}
	public boolean isPlayed() {
		return played;
	}
	public void setPlayed(boolean played) {
		this.played = played;
	}
	public Integer getActivityUserId() {
		return activityUserId;
	}
	public void setActivityUserId(Integer activityUserId) {
		this.activityUserId = activityUserId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
	

	
}
