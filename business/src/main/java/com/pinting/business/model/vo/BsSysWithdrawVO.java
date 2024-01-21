package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsSysWithdraw;

public class BsSysWithdrawVO extends BsSysWithdraw {
	
	private Date finishTimeBegin;
	private Date finishTimeEnd;
	private Date withdrawTimeBegin;
	private Date withdrawTimeEnd;
	public Date getFinishTimeBegin() {
		return finishTimeBegin;
	}
	public void setFinishTimeBegin(Date finishTimeBegin) {
		this.finishTimeBegin = finishTimeBegin;
	}
	public Date getFinishTimeEnd() {
		return finishTimeEnd;
	}
	public void setFinishTimeEnd(Date finishTimeEnd) {
		this.finishTimeEnd = finishTimeEnd;
	}
	public Date getWithdrawTimeBegin() {
		return withdrawTimeBegin;
	}
	public void setWithdrawTimeBegin(Date withdrawTimeBegin) {
		this.withdrawTimeBegin = withdrawTimeBegin;
	}
	public Date getWithdrawTimeEnd() {
		return withdrawTimeEnd;
	}
	public void setWithdrawTimeEnd(Date withdrawTimeEnd) {
		this.withdrawTimeEnd = withdrawTimeEnd;
	}
	
}
