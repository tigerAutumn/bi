package com.pinting.business.model.dto;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author zhangpeng
 * 2018-7-9
 */
public class ApplictionsDTO {
	
	private Integer userId;
	
	private String applications;
	
	private String address;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getApplications() {
		return applications;
	}

	public void setApplications(String applications) {
		this.applications = applications;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
}
