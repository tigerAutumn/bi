package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_System_AddSensitiveJnl extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3608464620006888937L;
		private Integer userId;
		private String opType;
		private String userName;
		private Date time;
		private String ip;
		private String terminal;
		private String info;
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public String getOpType() {
			return opType;
		}
		public void setOpType(String opType) {
			this.opType = opType;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public Date getTime() {
			return time;
		}
		public void setTime(Date time) {
			this.time = time;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getTerminal() {
			return terminal;
		}
		public void setTerminal(String terminal) {
			this.terminal = terminal;
		}
		public String getInfo() {
			return info;
		}
		public void setInfo(String info) {
			this.info = info;
		}
		
}
