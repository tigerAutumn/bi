package com.pinting.business.model.vo;

import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnUser;

/**
 * 还款分账-前置校验后返回结果
 * @project business
 * @author bianyatian
 * @2018-6-25 下午3:47:24
 */
public class RepaySplitVerifyReturnVO {

	private LnLoan lnLoan;
	
	private LnUser lnUser;
	
	private LnRepaySchedule repaySchedule;

	public LnLoan getLnLoan() {
		return lnLoan;
	}

	public void setLnLoan(LnLoan lnLoan) {
		this.lnLoan = lnLoan;
	}

	public LnUser getLnUser() {
		return lnUser;
	}

	public void setLnUser(LnUser lnUser) {
		this.lnUser = lnUser;
	}

	public LnRepaySchedule getRepaySchedule() {
		return repaySchedule;
	}

	public void setRepaySchedule(LnRepaySchedule repaySchedule) {
		this.repaySchedule = repaySchedule;
	}
	
	
	
}
