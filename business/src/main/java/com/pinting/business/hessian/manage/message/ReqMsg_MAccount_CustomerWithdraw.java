package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_CustomerWithdraw extends ReqMsg {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3382521396354358684L;
	private String applyNo;
	private double amount;
	private Date withdrawTime;
	private Date finishTime;
	private Integer status;
	private Integer startJnlNo;
	private Integer endJnlNo;
	
	private String failReason;
	
	
	public String getApplyNo() {
		return applyNo;
	}

	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(Date withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Integer getStartJnlNo() {
		return startJnlNo;
	}

	public void setStartJnlNo(Integer startJnlNo) {
		this.startJnlNo = startJnlNo;
	}

	public Integer getEndJnlNo() {
		return endJnlNo;
	}

	public void setEndJnlNo(Integer endJnlNo) {
		this.endJnlNo = endJnlNo;
	}

	public String getFailReason() {
		return failReason;
	}

	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}

	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	//排序
		private String orderField;
		private String orderDirection;
		
		public String getOrderField() {
			return orderField;
		}

		public void setOrderField(String orderField) {
			this.orderField = orderField;
		}

		public String getOrderDirection() {
			return orderDirection;
		}

		public void setOrderDirection(String orderDirection) {
			this.orderDirection = orderDirection;
		}
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	
}
