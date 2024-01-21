package com.pinting.business.model.vo;

import com.pinting.business.model.BsDafyFinanceChangeRecord;

public class BsDafyFinanceChangeRecordVO extends BsDafyFinanceChangeRecord {
	private Integer rowno;
	private String userName;
	private String mobile;
	private String opUserName;
	public Integer getRowno() {
		return rowno;
	}
	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOpUserName() {
		return opUserName;
	}
	public void setOpUserName(String opUserName) {
		this.opUserName = opUserName;
	}
}
