package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsSales_MatrixImage extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8246781210818113075L;
	
	private Integer id;
	
	private String name;
	
	private String link;
	
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
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

}
