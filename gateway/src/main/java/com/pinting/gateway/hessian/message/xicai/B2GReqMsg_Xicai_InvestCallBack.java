package com.pinting.gateway.hessian.message.xicai;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Xicai_InvestCallBack extends ReqMsg {

	private static final long serialVersionUID = -7537969850596951746L;
	
	private Integer subAccountId;

	private String phone;
	
	private Double commision;
	
	private Integer pid;
	
	private Double investamount;
	
	private Double earnings;
	
	public Double getCommision() {
		return commision;
	}

	public void setCommision(Double commision) {
		this.commision = commision;
	}

	public Double getInvestamount() {
		return investamount;
	}

	public void setInvestamount(Double investamount) {
		this.investamount = investamount;
	}

	public Double getEarnings() {
		return earnings;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
