package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RedPacket_RedPacketCheckGrid extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2792743141637352219L;

	private String serialNameSearch;

	private String distributeTypeSearch;

	private String checkStatus;

	private String status;

	private String serialNoSearch;

	private String policyTypeSearch;

	private String termLimitSearch;

	private String applicantTimeStart;
	private String applicantTimeEnd;

	private String amountMin;
	private String amountMax;

	private Integer pageNum = 1;

	private Integer numPerPage = 20;

	private Integer start = 1;

	public String getSerialNameSearch() {
		return serialNameSearch;
	}

	public void setSerialNameSearch(String serialNameSearch) {
		this.serialNameSearch = serialNameSearch;
	}

	public String getDistributeTypeSearch() {
		return distributeTypeSearch;
	}

	public void setDistributeTypeSearch(String distributeTypeSearch) {
		this.distributeTypeSearch = distributeTypeSearch;
	}

	public String getSerialNoSearch() {
		return serialNoSearch;
	}

	public void setSerialNoSearch(String serialNoSearch) {
		this.serialNoSearch = serialNoSearch;
	}

	public String getPolicyTypeSearch() {
		return policyTypeSearch;
	}

	public void setPolicyTypeSearch(String policyTypeSearch) {
		this.policyTypeSearch = policyTypeSearch;
	}

	public String getCheckStatus() {
		return checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getStart() {
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getApplicantTimeStart() {
		return applicantTimeStart;
	}

	public void setApplicantTimeStart(String applicantTimeStart) {
		this.applicantTimeStart = applicantTimeStart;
	}

	public String getApplicantTimeEnd() {
		return applicantTimeEnd;
	}

	public void setApplicantTimeEnd(String applicantTimeEnd) {
		this.applicantTimeEnd = applicantTimeEnd;
	}

	public String getAmountMin() {
		return amountMin;
	}

	public void setAmountMin(String amountMin) {
		this.amountMin = amountMin;
	}

	public String getAmountMax() {
		return amountMax;
	}

	public void setAmountMax(String amountMax) {
		this.amountMax = amountMax;
	}

	public String getTermLimitSearch() {
		return termLimitSearch;
	}

	public void setTermLimitSearch(String termLimitSearch) {
		this.termLimitSearch = termLimitSearch;
	}

}
