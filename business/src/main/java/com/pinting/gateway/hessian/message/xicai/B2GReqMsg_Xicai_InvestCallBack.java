package com.pinting.gateway.hessian.message.xicai;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Xicai_InvestCallBack extends ReqMsg {

	private static final long serialVersionUID = -7537969850596951746L;
	
	private Integer subAccountId; //本次投资的唯一编号

	private String phone; //用户手机
	
	private Double commision;  //p2p网贷平台返佣给希财网的金额
	
	private Integer pid; //投资的p2p网贷平台产品id
	
	private Double investamount; //用户投资金额
	
	private Double earnings; //用户投资收益（若无请填0）
	
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
