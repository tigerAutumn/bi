package com.pinting.business.model.vo;

import com.pinting.business.model.BsBonusGrantPlan;

public class BsBonusGrantPlanVO extends BsBonusGrantPlan {
	
	private String userIdFlag; //用户id校验标识，error表示格式校验不通过
	
	private String grantDateFlag; //发放日期校验标识，error表示格式校验不通过
	
	private String amountFlag; //金额校验标识，error表示格式校验不通过
	
	private String noteFlag; //备注校验标识，error表示格式校验不通过
	
	private String userIdStr; //前端表格显示
	
	private String amountStr;
	
	private String grantDateStr;

	public String getUserIdStr() {
		return userIdStr;
	}

	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}

	public String getAmountStr() {
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getGrantDateStr() {
		return grantDateStr;
	}

	public void setGrantDateStr(String grantDateStr) {
		this.grantDateStr = grantDateStr;
	}

	public String getUserIdFlag() {
		return userIdFlag;
	}

	public void setUserIdFlag(String userIdFlag) {
		this.userIdFlag = userIdFlag;
	}

	public String getGrantDateFlag() {
		return grantDateFlag;
	}

	public void setGrantDateFlag(String grantDateFlag) {
		this.grantDateFlag = grantDateFlag;
	}

	public String getNoteFlag() {
		return noteFlag;
	}

	public void setNoteFlag(String noteFlag) {
		this.noteFlag = noteFlag;
	}

	public String getAmountFlag() {
		return amountFlag;
	}

	public void setAmountFlag(String amountFlag) {
		this.amountFlag = amountFlag;
	}
	
	

}
