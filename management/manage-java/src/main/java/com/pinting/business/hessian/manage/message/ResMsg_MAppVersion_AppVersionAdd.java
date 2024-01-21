package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAppVersion_AppVersionAdd extends ResMsg {

	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -1877060465870165461L;
	
	private String json;
	
	/**
	 * 当前版本号高于库中最大的版本号
	 */
	public static final Integer VERSION_NUMBER_NEW = 1;
	
	/**
	 * 当前版本号低于库中最大的版本号
	 */
	public static final Integer VERSION_NUMBER_FAIL = -1;
	
	/**
	 * 当前版本号与库中最大的版本号相同
	 */
	public static final Integer VERSION_NUMBER_SAME = 0;
	
	private Integer flag;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}
