package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Agent_BsUserListQuery extends ReqMsg {
	
	private static final long serialVersionUID = -4025939148604502123L;
	private Integer status;
	/**
	 * 当前管理员id
	 */
	private Integer mUserId;
	private Integer sIsBindBank;
	private Integer sIsBindName;
	private String sNick;
	private String sEmail;
	private String sName;
	private String sReward;
	private String eReward;

	private String sRecommend;
	private String eRecommend;

	
	private Date sregistTime;
	private Date eregistTime;
	//排序
	private String orderField;
	private String orderDirection;

	private Integer totalRows;

	/* 分销渠道 */
	private String distributionChannel;

	private String queryDateFlag;

	/* 注册时终端 */
	private Integer regTerminal;

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

	public Date getSregistTime() {
		return sregistTime;
	}

	public void setSregistTime(Date sregistTime) {
		this.sregistTime = sregistTime;
	}

	public Date getEregistTime() {
		return eregistTime;
	}

	public void setEregistTime(Date eregistTime) {
		this.eregistTime = eregistTime;
	}
	public String getsRecommend() {
		return sRecommend;
	}

	public void setsRecommend(String sRecommend) {
		this.sRecommend = sRecommend;
	}

	public String geteRecommend() {
		return eRecommend;
	}

	public void seteRecommend(String eRecommend) {
		this.eRecommend = eRecommend;
	}

	

	public Integer getsIsBindBank() {
		return sIsBindBank;
	}

	public void setsIsBindBank(Integer sIsBindBank) {
		this.sIsBindBank = sIsBindBank;
	}

	public Integer getsIsBindName() {
		return sIsBindName;
	}

	public void setsIsBindName(Integer sIsBindName) {
		this.sIsBindName = sIsBindName;
	}

	public String getsNick() {
		return sNick;
	}

	public void setsNick(String sNick) {
		this.sNick = sNick;
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsReward() {
		return sReward;
	}

	public void setsReward(String sReward) {
		this.sReward = sReward;
	}

	public String geteReward() {
		return eReward;
	}

	public void seteReward(String eReward) {
		this.eReward = eReward;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	private String searchMobile;
	

	public String getSearchMobile() {
		return searchMobile;
	}

	public void setSearchMobile(String searchMobile) {
		this.searchMobile = searchMobile;
	}
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;
	/**
	 * 当前页码
	 */
	private int pageNum;

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getmUserId() {
		return mUserId;
	}

	public void setmUserId(Integer mUserId) {
		this.mUserId = mUserId;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public String getDistributionChannel() {
		return distributionChannel;
	}

	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}

	public String getQueryDateFlag() {
		return queryDateFlag;
	}

	public void setQueryDateFlag(String queryDateFlag) {
		this.queryDateFlag = queryDateFlag;
	}

	public Integer getRegTerminal() {
		return regTerminal;
	}

	public void setRegTerminal(Integer regTerminal) {
		this.regTerminal = regTerminal;
	}
}
