package com.pinting.business.model.vo;

import com.pinting.business.model.BsTag;

public class BsTagVO extends BsTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5336880212594862040L;
	
	private Integer mid;
	
	private String name;
	
	/** 该类型标签用户数 */
	private Integer countTag;
	
	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCountTag() {
		return countTag;
	}

	public void setCountTag(Integer countTag) {
		this.countTag = countTag;
	}

}
