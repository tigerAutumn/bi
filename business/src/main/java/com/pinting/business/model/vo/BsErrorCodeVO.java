package com.pinting.business.model.vo;

import com.pinting.business.model.BsErrorCode;

public class BsErrorCodeVO extends BsErrorCode {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7231364732404890525L;

	/** 渠道类型 */
	private String errorCodeChannel;

	/** 错误码 */
	private String errorCodeOther;
	
	/** 接口类型 */
	private String interfaceTypeOther;

	public String getErrorCodeChannel() {
		return errorCodeChannel;
	}

	public void setErrorCodeChannel(String errorCodeChannel) {
		this.errorCodeChannel = errorCodeChannel;
	}

	public String getErrorCodeOther() {
		return errorCodeOther;
	}

	public void setErrorCodeOther(String errorCodeOther) {
		this.errorCodeOther = errorCodeOther;
	}

	public String getInterfaceTypeOther() {
		return interfaceTypeOther;
	}

	public void setInterfaceTypeOther(String interfaceTypeOther) {
		this.interfaceTypeOther = interfaceTypeOther;
	}

}
