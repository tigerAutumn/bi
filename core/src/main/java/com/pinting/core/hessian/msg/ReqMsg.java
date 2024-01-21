package com.pinting.core.hessian.msg;

/***********************************************************************
 * Module:  ReqMsg.java
 * Author:  dingpf
 * Purpose: Defines the Class ReqMsg
 ***********************************************************************/

import java.io.Serializable;
import java.util.*;

/** 请求消息 */
public class ReqMsg implements Serializable{
	private static final long serialVersionUID = 1152167350333142115L;
	
	public static final String CHANNEL_MICRO = "micro";//手机端web
	public static final String CHANNEL_GEN = "gen";//PC端web
	/** 版本号 */
	private String version;
	/** 消息标识 */
	private String msgID;
	/** 交易码 */
	private String transCode;
	/** 渠道 */
	private String channel;
	/** 发送日期 */
	private String sendDate;
	/** 发送时间 */
	private String sendTime;
	/** 扩展字段 */
	private HashMap<String, Object> extendMap;
	
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public HashMap<String, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(HashMap<String, Object> extendMap) {
		this.extendMap = extendMap;
	}

}