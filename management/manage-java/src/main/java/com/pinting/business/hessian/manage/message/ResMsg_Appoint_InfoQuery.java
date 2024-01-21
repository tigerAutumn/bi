package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Appoint_InfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7060258972557116063L;
	
private Integer id;
	
	private String name;
	
	private String mobile;
	
	private String note;
	
	private String city;
	
	private Integer isDeal;

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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getIsDeal() {
		return isDeal;
	}

	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
}
