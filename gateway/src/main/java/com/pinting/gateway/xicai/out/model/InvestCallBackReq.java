package com.pinting.gateway.xicai.out.model;

public class InvestCallBackReq {

	private String id;

	private String phone;
	
	private String commision;
	
	private String pid;
	
	private String investamount;
	
	private String earnings;
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCommision() {
		return commision;
	}

	public void setCommision(String commision) {
		this.commision = commision;
	}

	public String getInvestamount() {
		return investamount;
	}

	public void setInvestamount(String investamount) {
		this.investamount = investamount;
	}

	public String getEarnings() {
		return earnings;
	}

	public void setEarnings(String earnings) {
		this.earnings = earnings;
	}
}
