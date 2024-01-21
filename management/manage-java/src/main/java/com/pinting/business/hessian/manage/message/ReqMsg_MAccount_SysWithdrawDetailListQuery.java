package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_SysWithdrawDetailListQuery extends ReqMsg{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3053349393424906356L;

	private Date finishTimeBegin;
	private Date finishTimeEnd;
	private Date withdrawTimeBegin;
	private Date withdrawTimeEnd;
	private Integer status;
	
	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;

	public int getPageNum() {
		if (pageNum < 1) {

			this.pageNum = 1;
		}

		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
}
