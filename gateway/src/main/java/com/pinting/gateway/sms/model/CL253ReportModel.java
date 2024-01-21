package com.pinting.gateway.sms.model;

public class CL253ReportModel {

	private String msgid;
	
	private String reportTime;
	
	private String mobile;
	
	private String status;
	
	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getReportTime() {
		return reportTime;
	}

	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CL253ReportModel [msgid=" + msgid + ", reportTime="
				+ reportTime + ", mobile=" + mobile + ", status=" + status
				+ "]";
	}


}
