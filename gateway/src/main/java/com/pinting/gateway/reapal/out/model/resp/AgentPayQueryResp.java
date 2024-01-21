package com.pinting.gateway.reapal.out.model.resp;

import java.util.List;

public class AgentPayQueryResp extends ReapalBaseOutResp {
	private String _input_charset;
	private String batchBizid;
	private String batchVersion;
	private String batchDate;
	private String batchCurrnum;
	private String sign;
	private String signType;
	
	private List<AgentPayDetail> details;

	public String get_input_charset() {
		return _input_charset;
	}

	public void set_input_charset(String _input_charset) {
		this._input_charset = _input_charset;
	}

	public String getBatchBizid() {
		return batchBizid;
	}

	public void setBatchBizid(String batchBizid) {
		this.batchBizid = batchBizid;
	}

	public String getBatchVersion() {
		return batchVersion;
	}

	public void setBatchVersion(String batchVersion) {
		this.batchVersion = batchVersion;
	}

	public String getBatchDate() {
		return batchDate;
	}

	public void setBatchDate(String batchDate) {
		this.batchDate = batchDate;
	}

	public String getBatchCurrnum() {
		return batchCurrnum;
	}

	public void setBatchCurrnum(String batchCurrnum) {
		this.batchCurrnum = batchCurrnum;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public List<AgentPayDetail> getDetails() {
		return details;
	}

	public void setDetails(List<AgentPayDetail> details) {
		this.details = details;
	}

	@Override
	public String toString() {
		return "AgentPayQueryResp [_input_charset=" + _input_charset
				+ ", batchBizid=" + batchBizid + ", batchVersion="
				+ batchVersion + ", batchDate=" + batchDate + ", batchCurrnum="
				+ batchCurrnum + ", sign=" + sign + ", signType=" + signType
				+ ", details=" + details + "]";
	}
	
}
