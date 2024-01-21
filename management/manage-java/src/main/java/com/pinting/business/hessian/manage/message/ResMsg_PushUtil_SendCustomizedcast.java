package com.pinting.business.hessian.manage.message;


import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_PushUtil_SendCustomizedcast extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 2349153500472492640L;
	
	private String androidSendResult;
	
	private String iosSendResult;

	public String getAndroidSendResult() {
		return androidSendResult;
	}

	public void setAndroidSendResult(String androidSendResult) {
		this.androidSendResult = androidSendResult;
	}

	public String getIosSendResult() {
		return iosSendResult;
	}

	public void setIosSendResult(String iosSendResult) {
		this.iosSendResult = iosSendResult;
	}
}
