package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MAccount_CheckJnlDetail extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415307688693249720L;
	
	private Integer id;
	
	private Integer transfJnlId;
	
	private Date time;
	
	private double sysAmount;
	
	private double doneAmount;
	
	private Integer result;
	
	private String info;
	
	private Integer userId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTransfJnlId() {
		return transfJnlId;
	}

	public void setTransfJnlId(Integer transfJnlId) {
		this.transfJnlId = transfJnlId;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public double getSysAmount() {
		return sysAmount;
	}

	public void setSysAmount(double sysAmount) {
		this.sysAmount = sysAmount;
	}

	public double getDoneAmount() {
		return doneAmount;
	}

	public void setDoneAmount(double doneAmount) {
		this.doneAmount = doneAmount;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
