package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AutoPacket_GetApply extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3482430262855994737L;
	
	private String rpName; //红包名称

    private String applyNo; //批次号

    private Double budget; //申请总金额
    
    private Double canPutAmount; //剩余金额

	public String getRpName() {
		return rpName;
	}

	public void setRpName(String rpName) {
		this.rpName = rpName;
	}

	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getCanPutAmount() {
		return canPutAmount;
	}

	public void setCanPutAmount(Double canPutAmount) {
		this.canPutAmount = canPutAmount;
	}

}
