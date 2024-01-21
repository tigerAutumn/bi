package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAccount_InvestMatureWarm extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3050525179332039831L;
	
	
	private String userName;
	
	private Date settleBeginTime;  //投资到期起始时间
	
	private Date settleEndTime;		//投资到期结束时间

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getSettleBeginTime() {
		return settleBeginTime;
	}

	public void setSettleBeginTime(Date settleBeginTime) {
		this.settleBeginTime = settleBeginTime;
	}

	public Date getSettleEndTime() {
		return settleEndTime;
	}

	public void setSettleEndTime(Date settleEndTime) {
		this.settleEndTime = settleEndTime;
	}

}
