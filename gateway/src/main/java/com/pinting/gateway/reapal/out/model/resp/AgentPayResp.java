package com.pinting.gateway.reapal.out.model.resp;

public class AgentPayResp extends ReapalBaseOutResp {
	
	private String status;
	private String reason;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}
