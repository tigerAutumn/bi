package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_ProductPlanCheck_PlanListQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2854113236123601884L;
	
	
	private String sName;
	
	private Integer sSerialId;
	
	private String sDistributeTime;
	
	private String eDistributeTime;
	
	private String sStartTime;
	
	private String eStartTime;
	
	private String sEndTime;
	
	private String eEndTime;
	
	private Integer sTerm;
	
	private Double sBaseRate;
	
	private Integer sStatus;
	
	private String sIsSuggest;
	
	private String serialName;
	
	private String termStr;
	
	private String checkerName;
	
	private String distributorName;
	
	private String sShowTerminal;
	
	
    private Integer start = 1;
	
	private Integer pageNum = 0;
	
	private Integer numPerPage = 20;
	
	private String orderField;
	
	private String orderDirection;

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

	public Integer getStart() {
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public Integer getsSerialId() {
		return sSerialId;
	}

	public void setsSerialId(Integer sSerialId) {
		this.sSerialId = sSerialId;
	}

	public String getsDistributeTime() {
		return sDistributeTime;
	}

	public void setsDistributeTime(String sDistributeTime) {
		this.sDistributeTime = sDistributeTime;
	}

	public String geteDistributeTime() {
		return eDistributeTime;
	}

	public void seteDistributeTime(String eDistributeTime) {
		this.eDistributeTime = eDistributeTime;
	}

	public String getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
	}

	public String geteStartTime() {
		return eStartTime;
	}

	public void seteStartTime(String eStartTime) {
		this.eStartTime = eStartTime;
	}

	public String getsEndTime() {
		return sEndTime;
	}

	public void setsEndTime(String sEndTime) {
		this.sEndTime = sEndTime;
	}

	public String geteEndTime() {
		return eEndTime;
	}

	public void seteEndTime(String eEndTime) {
		this.eEndTime = eEndTime;
	}

	public Integer getsTerm() {
		return sTerm;
	}

	public void setsTerm(Integer sTerm) {
		this.sTerm = sTerm;
	}

	public Double getsBaseRate() {
		return sBaseRate;
	}

	public void setsBaseRate(Double sBaseRate) {
		this.sBaseRate = sBaseRate;
	}

	public Integer getsStatus() {
		return sStatus;
	}

	public void setsStatus(Integer sStatus) {
		this.sStatus = sStatus;
	}

	public String getsIsSuggest() {
		return sIsSuggest;
	}

	public void setsIsSuggest(String sIsSuggest) {
		this.sIsSuggest = sIsSuggest;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getTermStr() {
		return termStr;
	}

	public void setTermStr(String termStr) {
		this.termStr = termStr;
	}

	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	public String getDistributorName() {
		return distributorName;
	}

	public void setDistributorName(String distributorName) {
		this.distributorName = distributorName;
	}

	public String getsShowTerminal() {
		return sShowTerminal;
	}

	public void setsShowTerminal(String sShowTerminal) {
		this.sShowTerminal = sShowTerminal;
	}
}
