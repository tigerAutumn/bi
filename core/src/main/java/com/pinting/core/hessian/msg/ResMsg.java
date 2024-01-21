package com.pinting.core.hessian.msg;

/***********************************************************************
 * Module:  ResMsg.java
 * Author:  dingpf
 * Purpose: Defines the Class ResMsg
 ***********************************************************************/

import java.io.Serializable;
import java.util.*;

/** 消息响应 */
public class ResMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1384295370980029481L;
	/** 返回码 */
	private String resCode;
	/** 返回信息 */
	private String resMsg;
	/** 版本号 */
	private String version;
	/** 消息标识 */
	private String msgID;
	/** 扩展字段 */
	private HashMap<String, Object> extendMap;
	
	public ResMsg(){
		
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMsgID() {
		return msgID;
	}

	public void setMsgID(String msgID) {
		this.msgID = msgID;
	}

	public HashMap<String, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(HashMap<String, Object> extendMap) {
		this.extendMap = extendMap;
	}

}