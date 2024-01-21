package com.pinting.gateway.hessian.message.hfbank;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Sms_CL253ReportNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1696024459870708366L;
	
	private String msgid;
	
	private Date reportTime;
	
	private String mobile;
	
	private String status;

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
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
	

}
