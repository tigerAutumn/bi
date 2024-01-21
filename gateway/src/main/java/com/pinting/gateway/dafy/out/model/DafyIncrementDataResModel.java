package com.pinting.gateway.dafy.out.model;

import java.util.List;

public class DafyIncrementDataResModel {

	private Integer code;
	
	private String msg;
	
	private Integer isNext; //0-无下一页，1-有下一页
	
	private List<Tbdatapermission> strDataPermissions;

	public Integer getIsNext() {
		return isNext;
	}

	public void setIsNext(Integer isNext) {
		this.isNext = isNext;
	}

	public List<Tbdatapermission> getStrDataPermissions() {
		return strDataPermissions;
	}

	public void setStrDataPermissions(List<Tbdatapermission> strDataPermissions) {
		this.strDataPermissions = strDataPermissions;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
