package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsSysAccountJnl;

public class BsSysAccountJnlVO extends BsSysAccountJnl {
	
	/** 序列化 */
	private static final long serialVersionUID = -7543196827265619895L;
	
	/** 交易金额 */
	private Double startTransAmount;
	
	private Double endTransAmount;
	
	/** 系统时间 */
	private Date beginTime;
	
	private Date overTime;
	
	/** 账务日期 */
	private Date startTransTime;
	
	private Date endTransTime;
	
	private String transOtherCode;

	public Double getStartTransAmount() {
		return startTransAmount;
	}

	public void setStartTransAmount(Double startTransAmount) {
		this.startTransAmount = startTransAmount;
	}

	public Double getEndTransAmount() {
		return endTransAmount;
	}

	public void setEndTransAmount(Double endTransAmount) {
		this.endTransAmount = endTransAmount;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getOverTime() {
		return overTime;
	}

	public void setOverTime(Date overTime) {
		this.overTime = overTime;
	}

	public Date getStartTransTime() {
		return startTransTime;
	}

	public void setStartTransTime(Date startTransTime) {
		this.startTransTime = startTransTime;
	}

	public Date getEndTransTime() {
		return endTransTime;
	}

	public void setEndTransTime(Date endTransTime) {
		this.endTransTime = endTransTime;
	}

	public String getTransOtherCode() {
		return transOtherCode;
	}

	public void setTransOtherCode(String transOtherCode) {
		this.transOtherCode = transOtherCode;
	}

}
